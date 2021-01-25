package com.jetherrodrigues.donusbank

import com.jetherrodrigues.donusbank.repository.AuthorityRepository
import org.hamcrest.Matchers
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


private const val API_V1_AUTHORITY = "/api/v1/authority"

@SpringBootTest
@AutoConfigureMockMvc
internal class AuthorityResourceTest {
    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var authorityRepository: AuthorityRepository

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @Transactional
    fun `should create user successfully`() {
        mockMvc.perform(MockMvcRequestBuilders.get(API_V1_AUTHORITY)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.[*].name")
                        .value(Matchers.hasItem("ROLE_USER")))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.[*].name")
                        .value(Matchers.hasItem("ROLE_CLIENT")))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.[*].name")
                        .value(Matchers.hasItem("ROLE_ADMIN")))
                .andDo(MockMvcResultHandlers.print())

        assertFalse(authorityRepository.findAll().isEmpty())
    }

}
