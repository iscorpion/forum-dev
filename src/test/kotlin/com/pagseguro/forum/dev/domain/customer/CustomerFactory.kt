package com.pagseguro.forum.dev.domain.customer

import com.pagseguro.forum.dev.domain.partner.Partner

object CustomerFactory {
    fun customer(
        id: Long? = null,
        codCustomer: String = "CUSTOMER:1",
        name: String = "John Silva",
        email: String = "loanuser@mock.com",
        personType: String = "PF",
        partners: MutableList<Partner> = mutableListOf()

    ): Customer {
        return Customer(id = id,
            codCustomer = codCustomer,
            name = name,
            email = email,
            personType = personType,
            partners = partners
        )
    }

}