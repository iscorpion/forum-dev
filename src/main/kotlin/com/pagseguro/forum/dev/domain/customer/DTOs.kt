package com.pagseguro.forum.dev.domain.customer

data class CustomerCreateRequest(
    val codCustomer: String,
    val name: String,
    val email: String,
    val personType: String
)

data class CustomerInfoResponse(
    val codCustomer: String,
    val name: String,
    val email: String,
)