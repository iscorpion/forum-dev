package com.pagseguro.forum.dev.domain.partner

import com.pagseguro.forum.dev.domain.customer.Customer
import com.pagseguro.forum.dev.domain.customer.CustomerService
import com.pagseguro.forum.dev.error.BusinessException
import com.pagseguro.forum.dev.error.PartnerNotFoundException
import org.springframework.stereotype.Service

@Service
class PartnerService(
    val customerService: CustomerService,
    val partnerRepository: PartnerRepository
) {

    fun createPartner(request: PartnerCreate) {
        val customer = customerService.getCustomer(request.codCustomer)

        if(customer.personType != "PJ") throw BusinessException(message = "Apenas para PJ")

        val partner = partnerRepository.save(Partner(codCustomer = request.codCustomer,
            name = request.name,
            ownership = request.ownership,
            customer = customer))

        customer.partners.add(partner)
        customerService.save(customer)
    }

    fun getPartners(customer: Customer): List<Partner> {
        if(customer.personType != "PJ") throw BusinessException(message = "Apenas para PJ")
        val partners =  customer.partners
        if(partners.isEmpty()) throw PartnerNotFoundException()
        return partners
    }
}