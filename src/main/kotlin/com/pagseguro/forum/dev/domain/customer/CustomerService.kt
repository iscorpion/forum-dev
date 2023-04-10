package com.pagseguro.forum.dev.domain.customer

import com.pagseguro.forum.dev.domain.partner.PartnerCreate
import com.pagseguro.forum.dev.domain.partner.Partner
import com.pagseguro.forum.dev.domain.partner.PartnerRepository
import com.pagseguro.forum.dev.error.BusinessException
import com.pagseguro.forum.dev.error.CustomerNotFoundException
import com.pagseguro.forum.dev.error.PartnerNotFoundException
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val repository: CustomerRepository,
    val partnerRepository: PartnerRepository
) {
    fun getCustomer(codCustomer: String): Customer {
        return repository.findByCodCustomer(codCustomer) ?: throw CustomerNotFoundException()
    }

    fun createCustomer(data: CustomerCreateRequest) {
        if(repository.existsByCodCustomer(data.codCustomer)) throw BusinessException(message = "invalid")
        repository.save(Customer(
            codCustomer = data.codCustomer,
            name = data.name,
            email = data.email,
            personType = data.personType))
    }

    fun createPartner(request: PartnerCreate) {
        val customer = repository.findByCodCustomer(request.codCustomer) ?: throw CustomerNotFoundException()

        if(customer.personType != "PJ") throw BusinessException(message = "Apenas para PJ")

        val partner = partnerRepository.save(Partner(codCustomer = request.codCustomer,
            name = request.name,
            ownership = request.ownership,
            customer = customer))

        customer.partners.add(partner)
        repository.save(customer)
    }

    fun getPartners(customer: Customer): List<Partner> {
        if(customer.personType != "PJ") throw BusinessException(message = "Apenas para PJ")
        val partners =  customer.partners
        if(partners.isEmpty()) throw PartnerNotFoundException()
        return partners
    }
}