package com.pagseguro.forum.dev.error

class PartnerNotFoundException(override val message: String = "Sócio não encontrado")
    : NotFoundException(message = message)