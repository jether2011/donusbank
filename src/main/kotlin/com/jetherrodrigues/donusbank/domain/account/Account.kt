package com.jetherrodrigues.donusbank.domain.account

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.PositiveOrZero

@Entity
@Table(name = "tb_account")
data class Account(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(nullable = false)
        val bank: String,
        @Column(nullable = false)
        val bankNumber: Int,
        val branch: Int = 1,
        @Column(unique = true, nullable = false)
        val number: String,
        @field:PositiveOrZero
        val amount: BigDecimal = BigDecimal.ZERO
) {
        fun addAmount(amount: BigDecimal) = amount.plus(amount)
        fun toWithdraw(amount: BigDecimal) = amount.minus(amount)
}
