package com.pagseguro.forum.dev.domain.partner

import org.springframework.data.jpa.repository.JpaRepository

interface PartnerRepository: JpaRepository<Partner, Long> {
    fun findByCodCustomer(codCustomer: String): List<Partner>
    fun existsByCodCustomer(codCustomer: String): Boolean
}