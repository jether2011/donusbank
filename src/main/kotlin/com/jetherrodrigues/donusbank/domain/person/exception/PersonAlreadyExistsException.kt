package com.jetherrodrigues.donusbank.domain.person.exception

data class PersonAlreadyExistsException(override val message: String) : RuntimeException(message)
