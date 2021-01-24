package com.jetherrodrigues.donusbank.domain.account

import com.jetherrodrigues.donusbank.domain.person.Person
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.PositiveOrZero

@Entity
@Table(name = "tb_account")
data class Account(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val branch: Int = 1,
        @Column(unique = true)
        val number: String,
        @PositiveOrZero
        val amount: BigDecimal,

        @OneToOne(optional = false)
        @JoinColumn(unique = true)
        val person: Person
)
