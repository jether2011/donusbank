package com.jetherrodrigues.donusbank.domain.account

import java.math.BigDecimal

data class AccountTransfer(
        val debitFrom: String,
        val favored: String,
        val amount: BigDecimal
)
