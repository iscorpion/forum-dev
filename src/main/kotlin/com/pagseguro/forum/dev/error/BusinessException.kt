package com.pagseguro.forum.dev.error

open class BusinessException(open val code: Int = 422, override val message: String?): RuntimeException()