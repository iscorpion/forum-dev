package com.pagseguro.forum.dev.error

open class NotFoundException(override val code: Int = 404, message: String?): BusinessException(code, message)