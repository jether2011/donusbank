package com.jetherrodrigues.donusbank.domain.account.service

import com.jetherrodrigues.donusbank.domain.account.Account
import com.jetherrodrigues.donusbank.domain.account.AccountTransfer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

interface AccountService {

    fun create(account: Account): Account

    fun findAll(pageable: Pageable): Page<Account>

    fun findById(id: Long): Account

    fun findByNumber(number: String): Account

    fun deposit(number: String, amount: BigDecimal)

    fun transfer(accountTransfer: AccountTransfer)

    fun getOne(accountId: Long): Account

}