package com.jetherrodrigues.donusbank.domain.auth.service

import com.jetherrodrigues.donusbank.domain.auth.User
import java.util.*

interface UserService {
    /**
     * Get user and his authorities
     *
     * @param username the username to get user
     */
    fun findOneWithAuthoritiesByUsername(username: String): Optional<User>

}