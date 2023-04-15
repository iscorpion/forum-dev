package com.pagseguro.forum.dev.domain.customer

import com.pagseguro.forum.dev.error.BusinessException
import com.pagseguro.forum.dev.error.CustomerNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val repository: CustomerRepository,
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

    fun save(customer: Customer) {
        repository.save(customer)
    }

}