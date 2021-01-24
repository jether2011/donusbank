package com.jetherrodrigues.donusbank.domain.auth

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_authority")
data class Authority(
        @Id
        @Column(length = 50)
        val name: String
)
