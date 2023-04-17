package com.pagseguro.forum.dev.domain.partner

import com.pagseguro.forum.dev.domain.customer.CustomerFactory
import com.pagseguro.forum.dev.domain.customer.CustomerService
import com.pagseguro.forum.dev.error.BusinessException
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PartnerServiceTest {
    @MockK lateinit var customerService: CustomerService
    @MockK lateinit var partnerRepository: PartnerRepository
    @InjectMockKs lateinit var partnerService: PartnerService

    @Test
    fun `create partner ok`() {
        val givenCustomer = CustomerFactory.customer(personType = "PJ")
        val givenRequest = PartnerCreate(givenCustomer.codCustomer, "John Fields", 1.0.toBigDecimal())

        every { customerService.getCustomer(givenCustomer.codCustomer) } returns givenCustomer
        every { customerService.save(any()) } returnsArgument 0
        every { partnerRepository.save(any()) } returnsArgument 0

        partnerService.createPartner(givenRequest)

        verify(exactly = 1) { customerService.save(givenCustomer) }
        verify(exactly = 1) { partnerRepository.save(any()) }
    }

    @Test
    fun `create partner for PF`() {
        val givenCustomer = CustomerFactory.customer()
        every { customerService.getCustomer(givenCustomer.codCustomer) } returns givenCustomer

        val givenRequest = PartnerCreate(codCustomer = givenCustomer.codCustomer,
            name = "João",
            ownership = 1.0.toBigDecimal())

        assertThrows<BusinessException> { partnerService.createPartner(givenRequest) }
        verify(exactly = 0) { partnerRepository.save(any()) }
        verify(exactly = 0) { customerService.save(any()) }

    }
}