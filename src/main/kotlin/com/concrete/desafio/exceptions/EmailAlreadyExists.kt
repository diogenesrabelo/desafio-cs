package com.concrete.desafio.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class EmailAlreadyExists {

    class JsonResponse(var mensagem: String)

    @ResponseBody
    @ExceptionHandler(EmailAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun emailAlreadyExistsHandler(ex: EmailAlreadyExistsException): ResponseEntity<JsonResponse>{
        return ResponseEntity<JsonResponse>(JsonResponse(ex.message.toString()), HttpStatus.CONFLICT)
    }

}

