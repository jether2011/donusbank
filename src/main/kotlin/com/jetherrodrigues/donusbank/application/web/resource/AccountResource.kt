package com.jetherrodrigues.donusbank.application.web.resource

import com.jetherrodrigues.donusbank.application.web.resource.request.AccountDepositRequest
import com.jetherrodrigues.donusbank.application.web.resource.request.AccountRequest
import com.jetherrodrigues.donusbank.application.web.resource.request.AccountTransferRequest
import com.jetherrodrigues.donusbank.application.web.resource.response.AccountResponse
import com.jetherrodrigues.donusbank.application.web.resource.validators.AccountDepositValidator
import com.jetherrodrigues.donusbank.domain.account.service.AccountService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

private const val API_V1_ACCOUNT = "/api/v1/account"

@RestController
@RequestMapping(API_V1_ACCOUNT)
class AccountResource(private val accountService: AccountService) {

    @InitBinder("accountDepositRequest")
    fun initBinder(binder: WebDataBinder) {
        binder.validator = AccountDepositValidator()
    }

    @PostMapping
    @Transactional
    fun createAccount(@Valid @RequestBody accountRequest: AccountRequest): ResponseEntity<AccountResponse> =
            accountRequest.let(AccountRequest::toAccount).run {
                accountService.create(this)
            }.let {
                ResponseEntity.created(URI("$API_V1_ACCOUNT/${it.id}"))
                        .body(AccountResponse.from(it))
            }

    @GetMapping("/{id}")
    fun getAccountById(@PathVariable("id") id: Long): ResponseEntity<AccountResponse> =
            accountService.findById(id).let {
                ResponseEntity.ok().body(AccountResponse.from(it))
            }

    @GetMapping("/{number}/account-number")
    fun getAccountByNumber(@PathVariable("number") number: String): ResponseEntity<AccountResponse> =
            accountService.findByNumber(number).let {
                ResponseEntity.ok().body(AccountResponse.from(it))
            }

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<AccountResponse>> =
            accountService.findAll(pageable).map { AccountResponse.from(it) }.let {
                ResponseEntity.ok().body(it)
            }

    @Transactional
    @PatchMapping("/{number}/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deposit(
            @PathVariable("number") number: String,
            @Valid @RequestBody accountDepositRequest: AccountDepositRequest
    ) = accountDepositRequest.let {
        accountService.deposit(number, accountDepositRequest.amount)
    }

    @Transactional
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun toWithDraw(@Valid @RequestBody accountTransferRequest: AccountTransferRequest) =
            accountTransferRequest.let(AccountTransferRequest::toCommand).run {
                accountService.transfer(this)
            }

}