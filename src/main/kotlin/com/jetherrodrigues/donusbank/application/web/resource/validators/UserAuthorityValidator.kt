package com.jetherrodrigues.donusbank.application.web.resource.validators

import com.jetherrodrigues.donusbank.application.web.error.exception.BadRequestException
import com.jetherrodrigues.donusbank.application.web.resource.request.UserRequest
import com.jetherrodrigues.donusbank.domain.auth.Authority
import com.jetherrodrigues.donusbank.domain.config.ExceptionMessage.AUTHORITIES_NOT_CONTAINS
import com.jetherrodrigues.donusbank.repository.AuthorityRepository
import org.springframework.validation.Errors
import org.springframework.validation.Validator

class UserAuthorityValidator(private val authorityRepository: AuthorityRepository) : Validator {

    override fun supports(clazz: Class<*>): Boolean =
            UserRequest::class == clazz

    @SuppressWarnings
    override fun validate(toValidate: Any, errors: Errors) {
        val request = toValidate as UserRequest

        val authorities: List<Authority> = authorityRepository.findAll()
        val requestAuthorities: List<Authority> = request.authorities.map { Authority(it) }

        if (!authorities.containsAll(requestAuthorities))
            throw BadRequestException(AUTHORITIES_NOT_CONTAINS.format(request.authorities, authorities))
    }

}