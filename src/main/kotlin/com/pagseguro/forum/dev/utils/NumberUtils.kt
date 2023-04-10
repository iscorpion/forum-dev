package com.pagseguro.forum.dev.utils

import java.math.BigDecimal
import java.math.RoundingMode


fun BigDecimal.setDefaultScale(): BigDecimal = this.setScale(2, RoundingMode.HALF_UP)

fun BigDecimal.toPercentage(): String {
    return "${this.times(100.0.toBigDecimal()).setDefaultScale()} %"
}