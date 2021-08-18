package com.concrete.desafio.constants

class SecurityConstants {
    companion object {
        const val SECRET = "SecretKeyToGenJWTs"
        const val EXPIRATION_TIME: Long = 1800000 // 30min
        const val TOKEN_PREFIX = "Bearer "
        const val HEADER_STRING = "Authorization"
        const val SIGN_UP_URL = "/users/sign-up"
    }
}