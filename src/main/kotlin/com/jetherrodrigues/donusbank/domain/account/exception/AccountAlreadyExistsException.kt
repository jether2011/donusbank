package com.jetherrodrigues.donusbank.domain.account.exception

data class AccountAlreadyExistsException(override val message: String) : RuntimeException(message)
