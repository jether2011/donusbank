package com.jetherrodrigues.donusbank.application.web.resource

import com.jetherrodrigues.donusbank.domain.auth.Authority
import com.jetherrodrigues.donusbank.repository.AuthorityRepository
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private const val API_V1_AUTHORITY = "/api/v1/authority"

@RestController
@RequestMapping(API_V1_AUTHORITY)
class AuthorityResource(private val authorityRepository: AuthorityRepository) {

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): ResponseEntity<List<Authority>> =
            authorityRepository.findAll().let {
                ResponseEntity.ok().body(it)
            }

}