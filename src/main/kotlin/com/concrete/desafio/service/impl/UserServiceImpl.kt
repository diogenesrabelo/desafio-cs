package com.concrete.desafio.service.impl

import com.concrete.desafio.dao.UserRepository
import com.concrete.desafio.model.User
import com.concrete.desafio.service.UserService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
open class UserServiceImpl(val userRepository: UserRepository): UserService {

    override fun createUser(user: User): User? {
        val created = LocalDateTime.now()
        val modified = created
        user.created = created
        user.modified = modified

        return userRepository.save(user)
    }

    override fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    override fun readUser(id: Long): Optional<User> = userRepository.findById(id)

    fun save(user: User?): User? {
        return if(user != null) {
            userRepository.save(user)
        }
        else{
            null
        }
    }
}