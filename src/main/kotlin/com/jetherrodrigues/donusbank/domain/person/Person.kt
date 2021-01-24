package com.jetherrodrigues.donusbank.domain.person

import javax.persistence.*

@Entity
@Table(name = "tb_person")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val name: String
)