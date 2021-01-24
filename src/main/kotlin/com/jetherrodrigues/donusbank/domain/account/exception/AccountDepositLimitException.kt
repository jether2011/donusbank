package com.jetherrodrigues.donusbank.domain.account.exception

data class AccountDepositLimitException(override val message: String) : RuntimeException(message)
