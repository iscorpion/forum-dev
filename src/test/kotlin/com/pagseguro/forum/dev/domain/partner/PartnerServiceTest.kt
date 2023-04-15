package com.pagseguro.forum.dev.domain.partner

import com.pagseguro.forum.dev.domain.customer.Customer
import com.pagseguro.forum.dev.domain.customer.CustomerService
import com.pagseguro.forum.dev.error.BusinessException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class PartnerServiceTest {
    @Mock lateinit var customerService: CustomerService
    @Mock lateinit var partnerRepository: PartnerRepository
    @InjectMocks lateinit var partnerService: PartnerService

    @Test
    fun `create partner ok`() {
        val givenCustomer = Customer(id = 1,
            codCustomer = "CUSTOMER:1",
            name = "Top Lanches",
            email = "toplanches@gmail.com",
            personType = "PJ")
        val givenRequest = PartnerCreate("CUSTOMER:1", "John Fields", 1.0.toBigDecimal())
        val expectedPartner = Partner(
            id = null,
            codCustomer = givenRequest.codCustomer,
            name = givenRequest.name,
            ownership = givenRequest.ownership,
            customer = givenCustomer)

        whenever(customerService.getCustomer(givenCustomer.codCustomer)).thenReturn(givenCustomer)
        whenever(partnerRepository.save(any())).thenReturn(expectedPartner)

        partnerService.createPartner(givenRequest)

        verify(customerService, times(1)).save(givenCustomer)
        verify(partnerRepository, times(1)).save(any())
    }

    @Test
    fun `create partner for PF`() {
        val givenCustomer = Customer(id = 1,
            codCustomer = "CUSTOMER:1",
            name = "Joao Silva",
            email = "js@gmail.com",
            personType = "PF")
        whenever(customerService.getCustomer(givenCustomer.codCustomer)).thenReturn(givenCustomer)

        val givenRequest = PartnerCreate(codCustomer = givenCustomer.codCustomer,
            name = "João",
            ownership = 1.0.toBigDecimal())

        assertThrows<BusinessException> { partnerService.createPartner(givenRequest) }
        verify(partnerRepository, never()).save(any())
        verify(customerService, never()).save(givenCustomer)
    }
}