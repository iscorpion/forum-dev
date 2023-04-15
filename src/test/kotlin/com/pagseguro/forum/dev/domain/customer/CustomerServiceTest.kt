package com.pagseguro.forum.dev.domain.customer

import com.pagseguro.forum.dev.error.BusinessException
import com.pagseguro.forum.dev.error.CustomerNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class CustomerServiceTest {

    @Mock lateinit var customerRepository: CustomerRepository
    @InjectMocks lateinit var customerService: CustomerService

    @Test
    fun `get customer returns ok`() {
        val givenCustomer = Customer(id = 1,
            codCustomer = "CUSTOMER:1",
            name = "Ptolomeu",
            email = "pto@gmail.com",
            personType = "PF")
        whenever(customerRepository.findByCodCustomer(any())).thenReturn(givenCustomer)

        val customer = customerService.getCustomer(givenCustomer.codCustomer)

        verify(customerRepository, times(1)).findByCodCustomer(any())
        assertThat(customer).isEqualTo(givenCustomer)
    }

    @Test
    fun `get customer not found`() {
        whenever(customerRepository.findByCodCustomer(any())).thenReturn(null)

        assertThrows<CustomerNotFoundException> {
            customerService.getCustomer("CUSTOMER:1")
        }
    }

    @Test
    fun `create customer ok`() {
        val givenRequest = CustomerCreateRequest(
            codCustomer = "CUSTOMER:1",
            name = "Ptolomeu",
            email = "pto@gmail.com",
            personType = "PF")
        customerService.createCustomer(givenRequest)

        verify(customerRepository, times(1)).save(any())
    }

    @Test
    fun `create customer already exists`() {
        val givenRequest = CustomerCreateRequest(
            codCustomer = "CUSTOMER:1",
            name = "Ptolomeu",
            email = "pto@gmail.com",
            personType = "PF")

        whenever(customerRepository.existsByCodCustomer(any())).thenReturn(true)
        assertThrows<BusinessException> { customerService.createCustomer(givenRequest) }

        verify(customerRepository, never()).save(any())
    }
}