package com.jetherrodrigues.donusbank.application.web.resource.response

import com.jetherrodrigues.donusbank.domain.person.Person

data class PersonResponse(
        val id: Long?,
        val name: String,
        val taxIdentifier: String,
        val type: String,
        val account: AccountResponse,
        val user: UserResponse
) {
    companion object {
        fun from(person: Person) = PersonResponse(
                id = person.id,
                name = person.name,
                taxIdentifier = person.taxIdentifier,
                type = person.type.name,
                account = AccountResponse.from(person.account),
                user = UserResponse.from(person.user)
        )
    }
}