package com.jetherrodrigues.donusbank.application.web.resource.request

import com.jetherrodrigues.donusbank.domain.auth.Authority
import com.jetherrodrigues.donusbank.domain.auth.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UserRequest(
        @field:NotEmpty @field:NotBlank @field:NotNull
        val username: String,
        @field:NotEmpty @field:NotBlank @field:NotNull
        val password: String,
        @field:NotEmpty @field:NotNull
        val authorities: Set<String>
) {
    fun toUser() = User(
            username = username,
            password = BCryptPasswordEncoder().encode(password),
            authorities = authorities.map { Authority(it) }.toSet()
    )
}
