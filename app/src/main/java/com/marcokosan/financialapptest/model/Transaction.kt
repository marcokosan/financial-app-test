package com.marcokosan.financialapptest.model

import java.math.BigDecimal
import java.sql.Timestamp

data class Transaction(
    val id: Long,
    val accountId: String,
    val value: BigDecimal,
    val description: String,
    val timestamp: Timestamp,
)