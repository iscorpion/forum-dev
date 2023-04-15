package com.pagseguro.forum.dev.domain.partner

import com.pagseguro.forum.dev.domain.customer.Customer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity
class Partner(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_partner")
    val id: Long? = null,

    @Column(name = "cod_customer")
    val codCustomer: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "ownership")
    val ownership: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "idt_customer")
    val customer: Customer
)