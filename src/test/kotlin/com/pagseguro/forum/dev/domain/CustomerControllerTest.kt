package com.pagseguro.forum.dev.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.pagseguro.forum.dev.controller.CustomerController
import com.pagseguro.forum.dev.domain.customer.Customer
import com.pagseguro.forum.dev.domain.customer.CustomerCreateRequest
import com.pagseguro.forum.dev.domain.customer.CustomerInfoResponse
import com.pagseguro.forum.dev.domain.customer.CustomerRepository
import com.pagseguro.forum.dev.domain.partner.PartnerCreate
import com.pagseguro.forum.dev.domain.partner.PartnerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerControllerTest {
    lateinit var customerController: CustomerController

    @Autowired lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var partnerRepository: PartnerRepository

    @Test
    fun `create customer`() {
        val request = CustomerCreateRequest(codCustomer = "CUSTOMER:1", name = "Clayton", email = "clay@gmail.com", personType = "PF")
        mockMvc.post("/customers") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
        }.andExpect { status { isNoContent() } }

        val customer = customerRepository.findByCodCustomer(request.codCustomer)
        assertThat(customer).isNotNull
        assertThat(customer?.name).isEqualTo(request.name)
    }

    @Test
    fun `get customer`(){
        val givenCustomer = Customer(
            codCustomer = "CUSTOMER:1", name = "Joseph", email = "Jj@gmail.com", personType = "PF")
        customerRepository.save(givenCustomer)
        val response = mockMvc.get("/customers/CUSTOMER:1") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk() } }.andReturn().response

        val customer = mapper.readValue(response.contentAsString, CustomerInfoResponse::class.java)
        assertThat(customer.email).isEqualTo(givenCustomer.email)
    }

    @Test
    fun `create partner`(){
        val givenCustomer = Customer(
            codCustomer = "CUSTOMER:1", name = "Joseph Lanches", email = "Jj@gmail.com", personType = "PJ")
        customerRepository.save(givenCustomer)
        val givenRequest = PartnerCreate(
            codCustomer = givenCustomer.codCustomer,
            name = "Joseph",
            ownership = 1.0.toBigDecimal())
        mockMvc.post("/customers/partners") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(givenRequest)
        }.andExpect { status { isNoContent() } }

        val partners = partnerRepository.findByCodCustomer(givenCustomer.codCustomer)
        assertThat(partners).hasSize(1)
        assertThat(partners.first().name).isEqualTo(givenRequest.name)
    }
}