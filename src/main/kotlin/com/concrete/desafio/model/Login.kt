package com.concrete.desafio.model

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Login(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        val email: String = "",
        val password: String = ""
) {
}