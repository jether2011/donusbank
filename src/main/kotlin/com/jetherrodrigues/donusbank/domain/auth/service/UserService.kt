package com.jetherrodrigues.donusbank.domain.auth.service

import com.jetherrodrigues.donusbank.domain.auth.User

interface UserService {

    fun create(user: User): User

    fun findOneWithAuthoritiesByUsername(username: String): User

}