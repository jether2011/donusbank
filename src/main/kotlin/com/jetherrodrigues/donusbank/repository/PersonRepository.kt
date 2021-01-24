package com.jetherrodrigues.donusbank.repository

import com.jetherrodrigues.donusbank.domain.person.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

    fun findByTaxIdentifier(taxIdentifier: String): Optional<Person>

}