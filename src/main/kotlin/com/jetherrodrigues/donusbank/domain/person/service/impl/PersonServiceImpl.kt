package com.jetherrodrigues.donusbank.domain.person.service.impl

import com.jetherrodrigues.donusbank.domain.config.ExceptionMessage.PERSON_ALREADY_EXISTS
import com.jetherrodrigues.donusbank.domain.exception.NotFoundException
import com.jetherrodrigues.donusbank.domain.person.Person
import com.jetherrodrigues.donusbank.domain.account.exception.AccountAlreadyExistsException
import com.jetherrodrigues.donusbank.domain.person.service.PersonService
import com.jetherrodrigues.donusbank.repository.PersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PersonServiceImpl(private val personRepository: PersonRepository) : PersonService {

    override fun create(person: Person): Person {
        personRepository.findByTaxIdentifier(person.taxIdentifier).ifPresent {
            throw AccountAlreadyExistsException(PERSON_ALREADY_EXISTS.format(it.taxIdentifier))
        }

        return personRepository.save(person)
    }

    override fun update(person: Person): Person {
        return personRepository.save(person)
    }

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<Person> {
        return personRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Person {
        return personRepository.findById(id).orElseThrow {
            throw NotFoundException("Person [Id: $id] not found")
        }
    }

    @Transactional(readOnly = true)
    override fun findByTaxIdentifier(taxIdentifier: String): Person {
        return personRepository.findByTaxIdentifier(taxIdentifier).orElseThrow {
            throw NotFoundException("Person [Tax identifier: $taxIdentifier] not found")
        }
    }

}