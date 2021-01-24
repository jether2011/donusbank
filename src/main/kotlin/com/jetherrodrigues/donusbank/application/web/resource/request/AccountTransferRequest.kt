package com.jetherrodrigues.donusbank.application.web.resource.request

import com.jetherrodrigues.donusbank.domain.account.AccountTransfer
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class AccountTransferRequest (
    @field:NotNull
    @field:PositiveOrZero
    val amount: BigDecimal,
    @field:NotNull
    @field:NotBlank
    @field:NotEmpty
    val debitFrom: String,
    @field:NotNull
    @field:NotBlank
    @field:NotEmpty
    val favored: String
) {
    fun toCommand() = AccountTransfer(
            amount = amount,
            debitFrom = debitFrom,
            favored = favored
    )
}
