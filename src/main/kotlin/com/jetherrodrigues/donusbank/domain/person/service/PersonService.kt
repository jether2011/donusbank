package com.jetherrodrigues.donusbank.domain.person.service

import com.jetherrodrigues.donusbank.domain.person.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PersonService {

    fun create(person: Person): Person

    fun update(person: Person): Person

    fun findAll(pageable: Pageable): Page<Person>

    fun findById(id: Long): Person

    fun findByTaxIdentifier(taxIdentifier: String): Person

}