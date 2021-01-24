package com.jetherrodrigues.donusbank.application.web.resource.response

import com.jetherrodrigues.donusbank.domain.account.Account
import java.math.BigDecimal

data class AccountResponse(
        val id: Long?,
        val branch: Int,
        val number: String,
        val amount: BigDecimal,
        val bank: String,
        val bankNumber: Int
) {
    companion object {
        fun from(account: Account) = AccountResponse(
                id = account.id,
                branch = account.branch,
                number = account.number,
                amount = account.amount,
                bank = account.bank,
                bankNumber = account.bankNumber
        )
    }
}