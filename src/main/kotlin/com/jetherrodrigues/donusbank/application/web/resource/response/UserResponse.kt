package com.jetherrodrigues.donusbank.application.web.resource.response

import com.jetherrodrigues.donusbank.domain.auth.User

data class UserResponse(
        val username: String,
        val authorities: List<String>
) {
    companion object {
        fun from(user: User) = UserResponse(
                username = user.username,
                authorities = user.authorities.map { it.name }
        )
    }
}