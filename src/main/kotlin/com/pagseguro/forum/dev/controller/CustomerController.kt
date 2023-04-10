package com.pagseguro.forum.dev.controller

import com.pagseguro.forum.dev.domain.customer.CustomerCreateRequest
import com.pagseguro.forum.dev.domain.customer.CustomerInfoResponse
import com.pagseguro.forum.dev.domain.customer.CustomerService
import com.pagseguro.forum.dev.domain.partner.PartnerCreate
import com.pagseguro.forum.dev.domain.partner.PartnerInfo
import com.pagseguro.forum.dev.domain.partner.PartnerInfoResponse
import com.pagseguro.forum.dev.utils.toPercentage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(val service: CustomerService) {
    @GetMapping("/{codCustomer}")
    fun getCustomer(@PathVariable codCustomer: String): ResponseEntity<CustomerInfoResponse> {
        val customer = service.getCustomer(codCustomer)
        return ResponseEntity.ok(
            CustomerInfoResponse(codCustomer = customer.codCustomer,
                name = customer.name,
                email = customer.email.orEmpty()
            )
        )
    }

    @PostMapping
    fun createCustomer(@RequestBody request: CustomerCreateRequest): ResponseEntity<Unit> {
        service.createCustomer(request)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/partners")
    fun createPartner(@RequestBody request: PartnerCreate): ResponseEntity<Unit> {
        service.createPartner(request)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{codCustomer}/partners")
    fun getPartners(@PathVariable codCustomer: String): ResponseEntity<PartnerInfoResponse> {
        val customer = service.getCustomer(codCustomer)
        val partners = service.getPartners(customer)
        val response = PartnerInfoResponse(codCustomer = customer.codCustomer,
            companyName = customer.name,
            companyEmail = customer.email.orEmpty(),
            partners = partners.map { PartnerInfo(it.name, it.ownership.toPercentage()) })
        return ResponseEntity.ok(response)
    }
}

