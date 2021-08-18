package com.concrete.desafio.service

import com.concrete.desafio.model.User
import java.util.*

interface UserService {

    fun createUser(user: User): User?

    fun readUser(id: Long): Optional<User>

    fun findByEmail(email: String): User?
}