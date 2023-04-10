package com.pagseguro.forum.dev.error

import org.springframework.context.MessageSource
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class ExceptionHandler(val source: MessageSource) {

    @ExceptionHandler(
        NotFoundException::class
    )
    fun handleNotFound(exception: NotFoundException): ResponseEntity<ApiError> {
        val errorCode = HttpStatus.valueOf(exception.code)

        return ResponseEntity.status(errorCode).body(ApiError(exception.code, exception.message))
    }

    @ExceptionHandler(
        BusinessException::class
    )
    fun handleBusinessException(exception: BusinessException): ResponseEntity<ApiError> {
        val errorCode = HttpStatus.valueOf(exception.code)

        return ResponseEntity.status(errorCode).body(ApiError(exception.code, exception.message))
    }
}