package com.pagseguro.forum.dev.domain.partner

import com.pagseguro.forum.dev.domain.customer.Customer
import com.pagseguro.forum.dev.domain.customer.CustomerFactory
import java.math.BigDecimal

object PartnerFactory {
    fun partner(
        id: Long? = null,
        codCustomer: String = "CUSTOMER:1",
        name: String = "John Partner",
        ownership: BigDecimal = 1.0.toBigDecimal(),
        customer: Customer?
    ): Partner {
        val givenCustomer = customer?:CustomerFactory.customer(id=1, personType = "PJ")
        return Partner(id = id,
            codCustomer = codCustomer,
            name = name,
            ownership = ownership,
            customer = givenCustomer)
    }
}