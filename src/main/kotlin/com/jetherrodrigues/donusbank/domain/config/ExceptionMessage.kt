package com.jetherrodrigues.donusbank.domain.config

object ExceptionMessage {

    const val ACCOUNT_ALREADY_EXISTS = "Account [number: %s ] already exists"
    const val PERSON_ALREADY_EXISTS = "Person [Tax identifier: %s ] already exists"
    const val USER_ALREADY_EXISTS = "User [username: %s ] already exists"
    const val AMOUNT_DEPOSIT_LIMIT = "The amount [%s] is bigger than the allowed deposit limit [%s]"
    const val AUTHORITIES_NOT_CONTAINS = "The authorities [%s] is not in [%s]"

}