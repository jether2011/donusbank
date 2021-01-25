package com.jetherrodrigues.donusbank.domain.account.service.impl

import com.jetherrodrigues.donusbank.domain.account.Account
import com.jetherrodrigues.donusbank.domain.account.AccountTransfer
import com.jetherrodrigues.donusbank.domain.account.exception.AccountAlreadyExistsException
import com.jetherrodrigues.donusbank.domain.account.service.AccountService
import com.jetherrodrigues.donusbank.domain.config.ExceptionMessage.ACCOUNT_ALREADY_EXISTS
import com.jetherrodrigues.donusbank.domain.exception.NotFoundException
import com.jetherrodrigues.donusbank.repository.AccountRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class AccountServiceImpl(private val accountRepository: AccountRepository) : AccountService {

    override fun create(account: Account): Account {
        accountRepository.findByNumber(account.number).ifPresent {
            throw AccountAlreadyExistsException(ACCOUNT_ALREADY_EXISTS.format(it.number))
        }

        return accountRepository.save(account)
    }

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<Account> = accountRepository.findAll(pageable)

    @Transactional(readOnly = true)
    override fun findById(id: Long): Account =
            accountRepository.findById(id).orElseThrow {
                throw NotFoundException("Account [Id: $id] not found")
            }

    @Transactional(readOnly = true)
    override fun findByNumber(number: String): Account =
            accountRepository.findByNumber(number).orElseThrow {
                throw NotFoundException("Account [Id: $number] not found")
            }

    override fun deposit(number: String, amount: BigDecimal) {
        this.findByNumber(number).let {
            accountRepository.save(it.copy(amount = it.amount.plus(amount)))
        }
    }

    override fun transfer(accountTransfer: AccountTransfer) {
        val toWithdraw = this.findByNumber(accountTransfer.debitFrom)
        val favored = this.findByNumber(accountTransfer.favored)

        accountRepository.save(toWithdraw.copy(amount = toWithdraw.amount.minus(accountTransfer.amount))).also {
            accountRepository.save(favored.copy(amount = favored.amount.plus(accountTransfer.amount)))
        }
    }

    @Transactional(readOnly = true)
    override fun getOne(accountId: Long): Account = accountRepository.getOne(accountId)

}
