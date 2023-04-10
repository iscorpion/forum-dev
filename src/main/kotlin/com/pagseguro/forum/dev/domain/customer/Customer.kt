package com.pagseguro.forum.dev.domain.customer

import com.pagseguro.forum.dev.domain.partner.Partner
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_customer")
    val id: Long? = null,

    @Column(name = "cod_customer")
    val codCustomer: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "email")
    var email: String?,

    @Column(name = "person_type")
    val personType: String,

    @OneToMany
    @JoinColumn(name = "idt_customer")
    val partners: MutableList<Partner> = mutableListOf()
)