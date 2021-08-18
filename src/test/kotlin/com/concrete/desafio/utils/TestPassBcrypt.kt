package com.concrete.desafio.utils

import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class TestPassBcrypt {
    private val SENHA = "123456"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun testGenerationBcrypt(){
        val hash = PassBcrypt().generationBcrypt(SENHA)
        assert(bCryptEncoder.matches(SENHA, hash))
    }
}