package com.jetherrodrigues.donusbank.application.web.resource.response

import com.jetherrodrigues.donusbank.domain.auth.User

data class UserResponse(val username: String) {
    companion object {
        fun from(user: User) = UserResponse(user.username)
    }
}