package com.jetherrodrigues.donusbank.application.web.resource.request

import com.jetherrodrigues.donusbank.domain.account.Account
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AccountRequest(
        @field:NotNull
        @field:NotBlank
        @field:NotEmpty
        val bank: String,
        @field:NotNull
        val bankNumber: Int,
        @field:NotNull
        val branch: Int,
        @field:NotNull
        @field:NotBlank
        @field:NotEmpty
        @field:Size(min = 6)
        val number: String
) {
    fun toAccount() = Account(
            bank = bank,
            bankNumber = bankNumber,
            number = number,
            branch = branch,
            amount = BigDecimal.ZERO
    )
}
