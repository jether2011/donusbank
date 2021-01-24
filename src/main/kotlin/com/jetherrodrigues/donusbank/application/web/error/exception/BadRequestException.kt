package com.jetherrodrigues.donusbank.application.web.error.exception

import java.lang.RuntimeException

data class BadRequestException(override val message: String) : RuntimeException(message)
