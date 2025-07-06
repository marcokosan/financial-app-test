package com.marcokosan.financialapptest.data.model

import java.math.BigDecimal

data class Account(
    val id: String,
    val holderName: String,
    val balance: BigDecimal,
)