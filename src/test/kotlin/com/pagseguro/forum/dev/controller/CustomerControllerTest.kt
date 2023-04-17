package com.pagseguro.forum.dev.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.pagseguro.forum.dev.domain.customer.CustomerCreateRequest
import com.pagseguro.forum.dev.domain.customer.CustomerFactory
import com.pagseguro.forum.dev.domain.customer.CustomerInfoResponse
import com.pagseguro.forum.dev.domain.customer.CustomerRepository
import com.pagseguro.forum.dev.domain.partner.PartnerFactory
import com.pagseguro.forum.dev.domain.partner.PartnerRepository
import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerControllerTest {
    @LocalServerPort
    lateinit var localServerPort: String

    @Autowired
    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var partnerRepository: PartnerRepository

    @Test
    fun `create customer`() {
        val request = CustomerCreateRequest(codCustomer = "CUSTOMER:1", name = "Clayton", email = "clay@gmail.com", personType = "PF")
        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .port(localServerPort.toInt())
            .body(request)
            .post("/customers")
            .andReturn()

        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT.value())

        val customer = customerRepository.findByCodCustomer(request.codCustomer)
        assertThat(customer).isNotNull
        assertThat(customer?.name).isEqualTo(request.name)
    }

    @Test
    fun `get customer`(){
        val givenCustomer = CustomerFactory.customer()
        customerRepository.save(givenCustomer)

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .port(localServerPort.toInt())
            .get("/customers/CUSTOMER:1")
            .andReturn()

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK.value())
        val customer = mapper.readValue(response.body.asString(), CustomerInfoResponse::class.java)
        assertThat(customer.email).isEqualTo(givenCustomer.email)
    }

    @Test
    fun `create partner`(){
        val givenCustomer = CustomerFactory.customer(personType = "PJ")
        customerRepository.save(givenCustomer)
        val givenRequest = PartnerFactory.partner(customer = givenCustomer)

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(givenRequest)
            .port(localServerPort.toInt())
            .post("/customers/partners")

        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT.value())

        val partners = partnerRepository.findByCodCustomer(givenCustomer.codCustomer)
        assertThat(partners).hasSize(1)
        assertThat(partners.first().name).isEqualTo(givenRequest.name)
    }
}