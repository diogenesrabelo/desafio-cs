package com.concrete.desafio.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PassBcrypt {
    fun generationBcrypt(senha: String) = BCryptPasswordEncoder().encode(senha)
}