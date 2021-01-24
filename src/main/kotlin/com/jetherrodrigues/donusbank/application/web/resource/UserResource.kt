package com.jetherrodrigues.donusbank.application.web.resource

import com.jetherrodrigues.donusbank.application.web.resource.request.UserRequest
import com.jetherrodrigues.donusbank.application.web.resource.response.UserResponse
import com.jetherrodrigues.donusbank.application.web.resource.validators.UserAuthorityValidator
import com.jetherrodrigues.donusbank.domain.auth.service.UserService
import com.jetherrodrigues.donusbank.repository.AuthorityRepository
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

private const val API_V1_USER = "/api/v1/user"

@RestController
@RequestMapping(API_V1_USER)
class UserResource(
        private val userService: UserService,
        private val authorityRepository: AuthorityRepository
) {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(UserAuthorityValidator(authorityRepository))
    }

    @PostMapping
    @Transactional
    fun createUser(@RequestBody @Valid request: UserRequest): ResponseEntity<UserResponse> =
            request.let(UserRequest::toUser).run {
                userService.create(this)
            }.let {
                ResponseEntity.created(URI("$API_V1_USER/${it.id}")).body(UserResponse.from(it))
            }

    @GetMapping("/{username}")
    fun getUserAndHisAuthorities(@PathVariable("username") username: String): ResponseEntity<UserResponse> =
            userService.findOneWithAuthoritiesByUsername(username).let {
                ResponseEntity.ok().body(UserResponse.from(it))
            }

}