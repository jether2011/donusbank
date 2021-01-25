package com.jetherrodrigues.donusbank

import com.fasterxml.jackson.databind.ObjectMapper
import com.jetherrodrigues.donusbank.application.web.resource.request.PersonRequest
import com.jetherrodrigues.donusbank.domain.person.service.PersonService
import com.jetherrodrigues.donusbank.domain.person.types.PersonType
import com.jetherrodrigues.donusbank.repository.AccountRepository
import com.jetherrodrigues.donusbank.repository.PersonRepository
import com.jetherrodrigues.donusbank.repository.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

private const val API_V1_PERSON = "/api/v1/person"
private const val TAX_IDENTIFIER = "11122233344"

@SpringBootTest
@AutoConfigureMockMvc
internal class PersonResourceTest {
    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var personRepository: PersonRepository
    @Autowired lateinit var personService: PersonService
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var accountRepository: AccountRepository

    private val mapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        personRepository.deleteAll()
        userRepository.deleteAll()
        accountRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @Transactional
    fun `should create person successfully`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val json = mapper.writeValueAsString(personRequest)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(personRequest.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.taxIdentifier").value(personRequest.taxIdentifier))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.type").value(PersonType.PF.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.account").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.user").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())

        assertFalse(personRepository.findAll().isEmpty())
    }

    @Test
    @Transactional
    fun `should throw bad request exception when try create person with the same tax identifier`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val json = mapper.writeValueAsString(personRequest)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(personRequest.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.taxIdentifier").value(personRequest.taxIdentifier))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.type").value(PersonType.PF.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.account").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.user").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())

        assertFalse(personRepository.findAll().isEmpty())

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Transactional
    fun `should update person successfully`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val json = mapper.writeValueAsString(personRequest)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(personRequest.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.taxIdentifier").value(personRequest.taxIdentifier))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.type").value(PersonType.PF.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.account").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.user").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())

        assertFalse(personRepository.findAll().isEmpty())

        val personToUpdate = personRepository.findByTaxIdentifier(personRequest.taxIdentifier).get()
        val jsonUpdate = mapper.writeValueAsString(personRequest.copy(
                id = personToUpdate.id,
                name = "User Name - Updated",
                taxIdentifier = personToUpdate.taxIdentifier,
                userId = personToUpdate.user.id!!,
                accountId = personToUpdate.account.id!!
        ))

        mockMvc.perform(MockMvcRequestBuilders.put(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
                .andExpect(MockMvcResultMatchers.status().isAccepted)

        val person = personService.findByTaxIdentifier(personRequest.taxIdentifier)

        assertTrue(person.name == personToUpdate.name)
    }

    @Test
    @Transactional
    fun `should get bad request when try update person without id`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val json = mapper.writeValueAsString(personRequest)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(personRequest.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.taxIdentifier").value(personRequest.taxIdentifier))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.type").value(PersonType.PF.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.account").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.user").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())

        assertFalse(personRepository.findAll().isEmpty())

        val personToUpdate = personRequest.copy(name = "User Name - Updated")
        val jsonUpdate = mapper.writeValueAsString(personToUpdate)

        mockMvc.perform(MockMvcRequestBuilders.put(API_V1_PERSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @Transactional
    fun `should find all person successfully`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val person = personService.create(personRequest.toPerson(user, account))

        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_V1_PERSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val result = resultActions.andReturn()
        val contentAsString = result.response.contentAsString

        assertTrue(contentAsString.contains(person.id.toString()))
        assertTrue(contentAsString.contains(person.name))
        assertTrue(contentAsString.contains(person.taxIdentifier))
        assertTrue(contentAsString.contains(person.type.toString()))
    }

    @Test
    @Transactional
    fun `should get person by id successfully`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val person = personService.create(personRequest.toPerson(user, account))

        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_PERSON/${person.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(person.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.taxIdentifier").value(person.taxIdentifier))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.type").value(PersonType.PF.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.account").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.user").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Transactional
    fun `should try get person by id but not found`() {
        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_PERSON/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @Transactional
    fun `should get person by tax identifier successfully`() {
        personRepository.deleteAll()
        val user = userRepository.save(USER_STUB.toUser())
        val account = accountRepository.save(ACCOUNT_PRIMARY_STUB.toAccount())

        val personRequest = PersonRequest(
                name = "User Name And Other Name",
                accountId = account.id!!,
                taxIdentifier = TAX_IDENTIFIER,
                userId = user.id!!
        )
        val person = personService.create(personRequest.toPerson(user, account))

        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_PERSON/${person.taxIdentifier}/tax-identifier"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(person.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.taxIdentifier").value(person.taxIdentifier))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.type").value(PersonType.PF.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.account").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.user").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Transactional
    fun `should try get person by tax identifier but not found`() {
        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_PERSON/12344455577/tax-identifier"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andDo(MockMvcResultHandlers.print())
    }

}