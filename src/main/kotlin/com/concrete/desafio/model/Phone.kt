package com.concrete.desafio.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Phone(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0L,
        val number: Int = 0,
        val ddd: Int = 0
){

}