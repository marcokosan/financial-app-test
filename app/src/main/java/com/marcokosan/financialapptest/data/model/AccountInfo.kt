package com.marcokosan.financialapptest.data.model

import java.math.BigDecimal

data class AccountInfo(
    val accountId: String,
    val holderName: String,
    val balance: BigDecimal,
)