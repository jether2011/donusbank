package com.jetherrodrigues.donusbank.domain.exception

data class NotFoundException(override val message: String) : RuntimeException(message)
