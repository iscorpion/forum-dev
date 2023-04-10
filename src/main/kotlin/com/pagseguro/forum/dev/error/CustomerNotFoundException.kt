package com.pagseguro.forum.dev.error

class CustomerNotFoundException(override val message: String = "Cliente não encontrado")
    : NotFoundException(message = message)