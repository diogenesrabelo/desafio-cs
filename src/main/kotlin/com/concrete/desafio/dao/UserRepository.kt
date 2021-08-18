package com.concrete.desafio.dao

import com.concrete.desafio.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long>{

    fun findByEmail(email: String): User?
    fun findByName(name: String): User?
}