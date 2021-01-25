package com.jetherrodrigues.donusbank

import com.fasterxml.jackson.databind.ObjectMapper
import com.jetherrodrigues.donusbank.application.web.resource.request.AccountDepositRequest
import com.jetherrodrigues.donusbank.application.web.resource.request.AccountRequest
import com.jetherrodrigues.donusbank.application.web.resource.request.AccountTransferRequest
import com.jetherrodrigues.donusbank.domain.account.service.AccountService
import com.jetherrodrigues.donusbank.repository.AccountRepository
import com.jetherrodrigues.donusbank.repository.PersonRepository
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
import java.math.BigDecimal


private const val API_V1_ACCOUNT = "/api/v1/account"

@SpringBootTest
@AutoConfigureMockMvc
internal class AccountResourceTest {
    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var personRepository: PersonRepository
    @Autowired lateinit var accountService: AccountService
    @Autowired lateinit var accountRepository: AccountRepository

    private val mapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        personRepository.deleteAll()
        accountRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @Transactional
    fun `should create accounts successfully`() {
        val primary = ACCOUNT_PRIMARY_STUB
        val account = primary.toAccount()
        val primaryJson = mapper.writeValueAsString(primary)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_ACCOUNT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(primaryJson))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.number").value(account.number))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.branch").value(account.branch))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bank").value(account.bank))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bankNumber").value(account.bankNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.amount").value(account.amount))
                .andDo(MockMvcResultHandlers.print())

        assertFalse(accountRepository.findAll().isEmpty())
    }

    @Test
    @Transactional
    fun `should throw bad request exception when try create account with the same number`() {
        val primary = ACCOUNT_PRIMARY_STUB
        val account = primary.toAccount()
        val primaryJson = mapper.writeValueAsString(primary)

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_ACCOUNT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(primaryJson))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.number").value(account.number))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.branch").value(account.branch))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bank").value(account.bank))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bankNumber").value(account.bankNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.amount").value(account.amount))
                .andDo(MockMvcResultHandlers.print())

        assertFalse(accountRepository.findAll().isEmpty())

        mockMvc.perform(MockMvcRequestBuilders.post(API_V1_ACCOUNT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(primaryJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Transactional
    fun `should get account by id successfully`() {
        val account = accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())

        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_ACCOUNT/${account.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.number").value(account.number))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.branch").value(account.branch))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bank").value(account.bank))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bankNumber").value(account.bankNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.amount").value(account.amount))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @Transactional
    fun `should get account by account number successfully`() {
        val account = accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())

        mockMvc.perform(MockMvcRequestBuilders.get("$API_V1_ACCOUNT/${account.number}/account-number"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.number").value(account.number))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.branch").value(account.branch))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bank").value(account.bank))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.bankNumber").value(account.bankNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.amount").value(account.amount))
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Transactional
    fun `should find all successfully`() {
        accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())
        val secondary = accountService.create(ACCOUNT_SECONDARY_STUB.toAccount())

        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_V1_ACCOUNT))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val result = resultActions.andReturn()
        val contentAsString = result.response.contentAsString

        assertTrue(contentAsString.contains(secondary.id.toString()))
        assertTrue(contentAsString.contains(secondary.number))
        assertTrue(contentAsString.contains(secondary.bank))
        assertTrue(contentAsString.contains(secondary.bankNumber.toString()))
        assertTrue(contentAsString.contains(secondary.branch.toString()))
        assertTrue(contentAsString.contains(secondary.amount.toString()))
    }

    @Test
    @Transactional
    fun `should perform deposit successfully`() {
        val account = accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())
        val depositJson = mapper.writeValueAsString(ACCOUNT_DEPOSIT_REQUEST_STUB)
        val expectedAmount = ACCOUNT_DEPOSIT_REQUEST_STUB.amount

        mockMvc.perform(MockMvcRequestBuilders.patch("$API_V1_ACCOUNT/${account.number}/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(depositJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted)

        assertTrue(accountService.findByNumber(account.number).amount == expectedAmount)
    }

    @Test
    @Transactional
    fun `should throw a bad request exception if amount is not allowed to perform deposit`() {
        val account = accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())
        val depositJson = mapper.writeValueAsString(ACCOUNT_DEPOSIT_REQUEST_STUB.copy(amount = BigDecimal.valueOf(2500.0)))

        mockMvc.perform(MockMvcRequestBuilders.patch("$API_V1_ACCOUNT/${account.number}/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(depositJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @Transactional
    fun `should perform transfer amount between accounts successfully`() {
        val primary = accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())

        val depositJson = mapper.writeValueAsString(ACCOUNT_DEPOSIT_REQUEST_STUB)
        mockMvc.perform(MockMvcRequestBuilders.patch("$API_V1_ACCOUNT/${primary.number}/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(depositJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted)

        val secondary = accountService.create(ACCOUNT_SECONDARY_STUB.toAccount())
        val transferJson = mapper.writeValueAsString(ACCOUNT_TRANSFER_REQUEST_STUB)
        val expectedPrimaryAmount = BigDecimal.valueOf(90.0)
        val expectedSecondaryAmount = BigDecimal.TEN

        mockMvc.perform(MockMvcRequestBuilders.post("$API_V1_ACCOUNT/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(transferJson))
                .andExpect(MockMvcResultMatchers.status().isAccepted)

        assertTrue(accountService.findByNumber(primary.number).amount == expectedPrimaryAmount)
        assertTrue(accountService.findByNumber(secondary.number).amount == expectedSecondaryAmount)
    }

    @Test
    @Transactional
    fun `should try perform transfer amount between accounts but not found the debitFrom account`() {
        accountService.create(ACCOUNT_PRIMARY_STUB.toAccount())
        accountService.create(ACCOUNT_SECONDARY_STUB.toAccount())
        val transferJson = mapper.writeValueAsString(ACCOUNT_TRANSFER_REQUEST_STUB.copy(debitFrom = "000111"))

        mockMvc.perform(MockMvcRequestBuilders.post("$API_V1_ACCOUNT/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(transferJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}

val ACCOUNT_PRIMARY_STUB = AccountRequest(
        bank = "DonusBank",
        bankNumber = 123,
        branch = 1,
        number = "123456"
)

val ACCOUNT_SECONDARY_STUB = AccountRequest(
        bank = "DonusBank",
        bankNumber = 123,
        branch = 1,
        number = "123789"
)

val ACCOUNT_DEPOSIT_REQUEST_STUB = AccountDepositRequest(BigDecimal.valueOf(100.0))

val ACCOUNT_TRANSFER_REQUEST_STUB = AccountTransferRequest(
        amount = BigDecimal.TEN,
        debitFrom = ACCOUNT_PRIMARY_STUB.number,
        favored = ACCOUNT_SECONDARY_STUB.number
)