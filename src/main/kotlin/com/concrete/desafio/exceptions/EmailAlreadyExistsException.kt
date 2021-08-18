package com.concrete.desafio.exceptions

import com.concrete.desafio.model.User

class EmailAlreadyExistsException(user: User): RuntimeException("Email ${user.email} já cadastrado")

