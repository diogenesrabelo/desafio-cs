package com.concrete.desafio.security

import com.concrete.desafio.constants.SecurityConstants
import com.concrete.desafio.model.User
import com.concrete.desafio.service.impl.UserServiceImpl
import com.concrete.desafio.utils.LocalDateTimeAdapter
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.GsonBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.LocalDateTime
import java.util.*
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.userdetails.User as UserD


class JWTAuthenticationFilter(authManager: AuthenticationManager, val userServiceImpl: UserServiceImpl) : UsernamePasswordAuthenticationFilter() {
    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(
            req: HttpServletRequest, res: HttpServletResponse): Authentication {
        val user: User = ObjectMapper().readValue(req.reader, User::class.java)
        val auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        user.email,
                        user.password,
                        emptyList<GrantedAuthority>()
                )
        )

        return auth
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            req: HttpServletRequest,
            res: HttpServletResponse, chain: FilterChain?,
            auth: Authentication) {
        val JWT = Jwts.builder()
                .setSubject((auth.principal as UserD).username)
                .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact()
        val user1: User? = userServiceImpl.findByEmail((auth.principal as UserD).username)
        user1?.token = JWT
        user1?.last_login = LocalDateTime.now()
        val gson = GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
                .create()
        userServiceImpl.save(user1)
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + " " + JWT)
        setConfResponse(res, HttpStatus.CREATED.value())
        res.writer.write(
                gson.toJson(user1)
        );
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: org.springframework.security.core.AuthenticationException?) {
       setConfResponse(response, HttpStatus.UNAUTHORIZED.value())
        response.writer.write(USUARIO_OU_SENHA_INVALIDOS)
    }

    fun setConfResponse(response: HttpServletResponse, httpStatus: Int): HttpServletResponse{
        response.status =  httpStatus
        response.contentType = CONTENT_TYPE
        response.characterEncoding = CHARACTER_ENCODING
        return response
    }

    companion object {
        const val CONTENT_TYPE = "application/json"
        const val CHARACTER_ENCODING = "UTF-8"
        const val USUARIO_OU_SENHA_INVALIDOS: String = "{ \"mensagem\": \"Usuário e/ou senha inválidos\" }"
    }
}