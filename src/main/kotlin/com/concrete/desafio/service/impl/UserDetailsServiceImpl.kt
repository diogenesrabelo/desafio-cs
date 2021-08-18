package com.concrete.desafio.service.impl

import com.concrete.desafio.dao.UserRepository
import com.concrete.desafio.exceptions.UnauthorizeException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import com.concrete.desafio.model.User as UserModel

@Service
open class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {
    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException(username)
        return User(user.email, user.password, emptyList())
    }

    fun save(user: UserModel): UserModel? {

        return userRepository.save(user)
    }

    fun findById(id: Long): Optional<UserModel> {
        return userRepository.findById(id)
    }

    fun findByEmail(email: String): UserModel?{
        return userRepository.findByEmail(email)
    }

    fun findUserActive(id: Long, token: String): UserModel?{
        val user: UserModel? = userRepository.findByIdOrNull(id)
        if(token.isEmpty()) throw UnauthorizeException(UNAUTHORIZED)
        if(!user?.token.equals(token.substring(START_TOKEN))) throw UnauthorizeException(UNAUTHORIZED)
        if(calcTime(user?.last_login) >= 30L) return throw UnauthorizeException(InvalidSession)
        return user
    }

    fun calcTime(startDateTime: LocalDateTime?):Long{
        val tempDateTime: LocalDateTime = LocalDateTime.from(startDateTime)
        println("MINUTES ${tempDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES)}")
        return tempDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES)
    }

    companion object {
        const val START_TOKEN: Int = 7
        const val UNAUTHORIZED = "Não Autorizado"
        const val InvalidSession = "Sessão inválida"
    }
}