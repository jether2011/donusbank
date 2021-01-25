package com.jetherrodrigues.donusbank

import com.fasterxml.jackson.databind.ObjectMapper
import com.jetherrodrigues.donusbank.application.web.resource.request.UserRequest
import com.jetherrodrigues.donusbank.domain.auth.Authority
import com.jetherrodrigues.donusbank.domain.auth.service.UserService
import com.jetherrodrigues.donusbank.repository.PersonRepository
import com.jetherrodrigues.donusbank.repository.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional


private const val API_V1_USER = "/api/v1/user"

@SpringBootTest
@AutoConfigureMockMvc
internal class UserResourceTest {
    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var personRepository: PersonRepository
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var userRepository: UserRepository

    private val mapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        personRepository.deleteAll()
        userRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @Transactional
    fun `should create user successfully`() {
        val userRequest = USER_STUB
        val user = userRequest.toUser()
        val json = mapper.writeValueAsString(userRequest)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.username").value(user.username))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.authorities").value(userRequest.authorities.map { it }))
                .andDo(MockMvcResultHandlers.print())

        assertFalse(userRepository.findAll().isEmpty())
    }

    @Test
    @Transactional
    fun `should throw bad request exception when try create user with the same username`() {
        val userRequest = USER_STUB
        val user = userRequest.toUser()
        val json = mapper.writeValueAsString(userRequest)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.username").value(user.username))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.authorities").value(userRequest.authorities.map { it }))
                .andDo(MockMvcResultHandlers.print())

        assertFalse(userRepository.findAll().isEmpty())

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Transactional
    fun `should get user by username successfully`() {
        val userRequest = USER_STUB
        val user = userRepository.save(userRequest.toUser())

        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_USER/${userRequest.username}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.username").value(user.username))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.authorities").value(userRequest.authorities.map { it }))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @Transactional
    fun `should try get user by username but not found`() {
        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_USER/some.user"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andDo(MockMvcResultHandlers.print())

    }

}

val USER_STUB = UserRequest(
        username = "user",
        password = "user123",
        authorities = setOf("ROLE_USER")
)