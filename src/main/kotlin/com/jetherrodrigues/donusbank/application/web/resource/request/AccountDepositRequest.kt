package com.jetherrodrigues.donusbank.application.web.resource.request

import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class AccountDepositRequest(
        @field:NotNull
        @field:PositiveOrZero
        val amount: BigDecimal
)