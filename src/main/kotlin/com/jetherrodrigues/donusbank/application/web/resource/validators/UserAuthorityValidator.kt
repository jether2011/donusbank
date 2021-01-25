package com.jetherrodrigues.donusbank.application.web.resource.validators

import com.jetherrodrigues.donusbank.application.web.error.exception.BadRequestException
import com.jetherrodrigues.donusbank.application.web.resource.request.UserRequest
import com.jetherrodrigues.donusbank.domain.config.ExceptionMessage.AUTHORITIES_NOT_CONTAINS
import com.jetherrodrigues.donusbank.repository.AuthorityRepository
import org.springframework.validation.Errors
import org.springframework.validation.Validator

class UserAuthorityValidator(private val authorityRepository: AuthorityRepository) : Validator {

    override fun supports(clazz: Class<*>): Boolean = UserRequest::class.java.isAssignableFrom(clazz)

    @SuppressWarnings
    override fun validate(toValidate: Any, errors: Errors) {
        val request = toValidate as UserRequest

        val authorities: Set<String> = authorityRepository.findAll().map { it.name }.toSet()
        val requestAuthorities: Set<String> = request.authorities

        if (!authorities.containsAll(requestAuthorities))
            throw BadRequestException(AUTHORITIES_NOT_CONTAINS.format(request.authorities, authorities))
            // errors.rejectValue("authorities", AUTHORITIES_NOT_CONTAINS.format(request.authorities, authorities))
    }

}