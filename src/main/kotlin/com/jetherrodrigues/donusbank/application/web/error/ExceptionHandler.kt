package com.jetherrodrigues.donusbank.application.web.error

import com.fasterxml.jackson.annotation.JsonFormat
import com.jetherrodrigues.donusbank.application.web.error.exception.BadRequestException
import com.jetherrodrigues.donusbank.domain.exception.NotFoundException
import com.jetherrodrigues.donusbank.domain.account.exception.AccountAlreadyExistsException
import com.jetherrodrigues.donusbank.domain.account.exception.AccountDepositLimitException
import com.jetherrodrigues.donusbank.domain.auth.exception.UserAlreadyExistsException
import com.jetherrodrigues.donusbank.domain.person.exception.PersonAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException


@RestControllerAdvice
class ExceptionHandler {

    companion object{
        const val ENTITY_ALREADY_EXISTS = "ENTITY_ALREADY_EXISTS"
        const val NOT_FOUND = "NOT_FOUND"
        const val BAD_REQUEST = "BAD_REQUEST"
    }

    @ExceptionHandler(value = [
        AccountAlreadyExistsException::class,
        UserAlreadyExistsException::class,
        PersonAlreadyExistsException::class
    ])
    fun handleEntityAlreadyExistsException(e: RuntimeException): ResponseEntity<ErrorResponse> =
            ResponseEntity<ErrorResponse>(ErrorResponse(ENTITY_ALREADY_EXISTS,
                    e.message, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [
        BadRequestException::class,
        AccountDepositLimitException::class
    ])
    fun handleBadRequestException(e: RuntimeException): ResponseEntity<ErrorResponse> =
            ResponseEntity<ErrorResponse>(ErrorResponse(BAD_REQUEST,
                    e.message, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [
        NotFoundException::class,
        EntityNotFoundException::class
    ])
    fun handleNotFoundException(e: RuntimeException): ResponseEntity<ErrorResponse> =
            ResponseEntity<ErrorResponse>(ErrorResponse(NOT_FOUND,
                    e.message, HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND)

}

data class ErrorResponse(
        val error: String,
        val message: String?,
        val status: Int,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        val timestamp: LocalDateTime = LocalDateTime.now()
)