package com.concrete.desafio.controller

import com.concrete.desafio.exceptions.EmailAlreadyExistsException
import com.concrete.desafio.model.User
import com.concrete.desafio.service.impl.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
        val userService: UserDetailsServiceImpl,
        val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    fun createUser(@RequestBody user: User): User? {
        if (userService.findByEmail(user.email) != null) {
            throw EmailAlreadyExistsException(user)
        }
        user.password = bCryptPasswordEncoder.encode(user.password)
        return userService.save(user)
    }

    @GetMapping("/{id}")
    fun userFindById(@PathVariable id: Long, @RequestHeader("Authorization") token: String): User? {
        return userService.findUserActive(id, token)
    }


}