package com.concrete.desafio.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0L,
        val name: String = "",
        @Column(unique = true)
        val email: String = "",
        var password: String = "",
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
        @JoinColumn(name = "phone_id", referencedColumnName="id",
                insertable = true, updatable = true)
        val phones: Set<Phone>? = null,
        var created: LocalDateTime? = LocalDateTime.now(),
        var modified: LocalDateTime? = LocalDateTime.now(),
        var last_login: LocalDateTime? = null,
        var token: String? = null
        ){
}