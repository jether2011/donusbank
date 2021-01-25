package com.jetherrodrigues.donusbank.application.web.resource

import com.jetherrodrigues.donusbank.application.web.error.exception.BadRequestException
import com.jetherrodrigues.donusbank.application.web.resource.request.PersonRequest
import com.jetherrodrigues.donusbank.application.web.resource.response.PersonResponse
import com.jetherrodrigues.donusbank.domain.account.service.AccountService
import com.jetherrodrigues.donusbank.domain.auth.service.UserService
import com.jetherrodrigues.donusbank.domain.person.service.PersonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

private const val API_V1_PERSON = "/api/v1/person"

@RestController
@RequestMapping(API_V1_PERSON)
class PersonResource(
        private val personService: PersonService,
        private val userService: UserService,
        private val accountService: AccountService
) {

    @PostMapping
    @Transactional
    fun createPerson(@RequestBody @Valid request: PersonRequest): ResponseEntity<PersonResponse> {
        val user = userService.getOne(request.userId)
        val account = accountService.getOne(request.accountId)

        return request.toPerson(user, account).run {
            personService.create(this)
        }.let {
            ResponseEntity.created(URI("$API_V1_PERSON/${it.id}"))
                    .body(PersonResponse.from(it))
        }
    }

    @PutMapping
    @Transactional
    fun updatePerson(@RequestBody @Valid request: PersonRequest): ResponseEntity<PersonResponse> {
        if (request.id == null)
            throw BadRequestException("To update person the ID must be not null")

        val user = userService.getOne(request.userId)
        val account = accountService.getOne(request.accountId)

        return request.toPerson(user, account).run {
            personService.update(this)
        }.let {
            ResponseEntity.accepted().body(PersonResponse.from(it))
        }
    }

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable("id") id: Long): ResponseEntity<PersonResponse> =
            personService.findById(id).let {
                ResponseEntity.ok().body(PersonResponse.from(it))
            }

    @GetMapping("/{taxIdentifier}/tax-identifier")
    fun getPersonByTaxIdentifier(@PathVariable("taxIdentifier") taxIdentifier: String): ResponseEntity<PersonResponse> =
            personService.findByTaxIdentifier(taxIdentifier).let {
                ResponseEntity.ok().body(PersonResponse.from(it))
            }

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<PersonResponse>> =
            personService.findAll(pageable).map { PersonResponse.from(it) }.let {
                ResponseEntity.ok().body(it)
            }

}