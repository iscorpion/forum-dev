package com.pagseguro.forum.dev.domain.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
    fun findByCodCustomer(codCustomer: String): Customer?
    fun existsByCodCustomer(codCustomer: String): Boolean
}
