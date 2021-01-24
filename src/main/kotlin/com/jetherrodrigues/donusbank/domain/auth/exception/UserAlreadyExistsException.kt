package com.jetherrodrigues.donusbank.domain.auth.exception

data class UserAlreadyExistsException(override val message: String) : RuntimeException(message)
