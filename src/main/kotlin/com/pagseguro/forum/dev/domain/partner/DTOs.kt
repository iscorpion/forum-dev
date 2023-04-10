package com.pagseguro.forum.dev.domain.partner

import java.math.BigDecimal

data class PartnerCreate(
    val codCustomer: String,
    val name: String,
    val ownership: BigDecimal
)

data class PartnerInfoResponse(
    val codCustomer: String,
    val companyName: String,
    val companyEmail: String,
    val partners: List<PartnerInfo>
)

data class PartnerInfo(val name: String, val ownership: String)