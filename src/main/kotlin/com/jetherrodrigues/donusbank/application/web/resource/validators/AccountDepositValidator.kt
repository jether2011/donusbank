package com.jetherrodrigues.donusbank.application.web.resource.validators

import com.jetherrodrigues.donusbank.application.web.resource.request.AccountDepositRequest
import com.jetherrodrigues.donusbank.common.isBiggerThan
import com.jetherrodrigues.donusbank.domain.account.exception.AccountDepositLimitException
import com.jetherrodrigues.donusbank.domain.config.ExceptionMessage.AMOUNT_DEPOSIT_LIMIT
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.math.BigDecimal

private val SECURITY_AMOUNT_DEPOSIT_LIMIT = BigDecimal.valueOf(2000.0)

class AccountDepositValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean = AccountDepositRequest::class.java.isAssignableFrom(clazz)

    override fun validate(toValidate: Any, errors: Errors) {
        val request = toValidate as AccountDepositRequest

        val amountLimitSecurityValidate = request.amount.isBiggerThan(SECURITY_AMOUNT_DEPOSIT_LIMIT)
        if(amountLimitSecurityValidate)
            throw AccountDepositLimitException(AMOUNT_DEPOSIT_LIMIT
                    .format(request.amount, SECURITY_AMOUNT_DEPOSIT_LIMIT))
    }

}