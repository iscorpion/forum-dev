package com.pagseguro.forum.dev.domain.customer

import com.pagseguro.forum.dev.error.BusinessException
import com.pagseguro.forum.dev.error.CustomerNotFoundException
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRepository: CustomerRepository
    @InjectMockKs lateinit var customerService: CustomerService

    @Test
    fun `get customer returns ok`() {
        val givenCustomer = CustomerFactory.customer()
        every { customerRepository.findByCodCustomer(any()) } returns givenCustomer

        val customer = customerService.getCustomer(givenCustomer.codCustomer)

        verify(exactly = 1) { customerRepository.findByCodCustomer(any()) }
        assertThat(customer).isEqualTo(givenCustomer)
    }

    @Test
    fun `get customer not found`() {
        every { customerRepository.findByCodCustomer(any()) } returns null

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
        every { customerRepository.existsByCodCustomer(any()) } returns false
        every { customerRepository.save(any()) } returnsArgument 0
        customerService.createCustomer(givenRequest)

        verify(exactly = 1) { customerRepository.save(any()) }
    }

    @Test
    fun `create customer already exists`() {
        val givenRequest = CustomerCreateRequest(
            codCustomer = "CUSTOMER:1",
            name = "Ptolomeu",
            email = "pto@gmail.com",
            personType = "PF")

        every { customerRepository.existsByCodCustomer(any()) } returns true
        assertThrows<BusinessException> { customerService.createCustomer(givenRequest) }

        verify { customerRepository.save(any()) wasNot Called }
    }
}
