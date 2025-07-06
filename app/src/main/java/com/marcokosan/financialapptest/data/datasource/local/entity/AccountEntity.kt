package com.marcokosan.financialapptest.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    val id: String,
    val holderName: String,
    val balance: BigDecimal,
)