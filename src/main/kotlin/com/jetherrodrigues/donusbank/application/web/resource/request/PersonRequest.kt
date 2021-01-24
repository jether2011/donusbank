package com.jetherrodrigues.donusbank.application.web.resource.request

import com.jetherrodrigues.donusbank.domain.account.Account
import com.jetherrodrigues.donusbank.domain.auth.User
import com.jetherrodrigues.donusbank.domain.person.Person
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class PersonRequest(
        val id: Long? = null,
        @field:NotNull
        @field:NotBlank
        @field:NotEmpty
        val name: String,
        @field:NotNull
        @field:NotBlank
        @field:NotEmpty
        @field:Size(min = 11, max = 11)
        val taxIdentifier: String,
        @field:NotNull
        val user: User,
        @field:NotNull
        val account: Account
) {
    fun toPerson() = Person(
            id = id,
            name = name,
            taxIdentifier = taxIdentifier,
            account = account,
            user = user
    )
}
