package com.marcokosan.financialapptest.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "balance")
data class AccountInfoEntity(
    @PrimaryKey val accountId: String,
    val holderName: String,
    val balance: BigDecimal,
)