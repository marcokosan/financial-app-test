package com.marcokosan.financialapptest.model

import java.math.BigDecimal

data class Account(
    val id: String,
    val holderName: String,
    val balance: BigDecimal,
)