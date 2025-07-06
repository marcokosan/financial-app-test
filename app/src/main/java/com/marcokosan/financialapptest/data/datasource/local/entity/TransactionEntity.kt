package com.marcokosan.financialapptest.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.sql.Timestamp

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val accountId: String,
    val value: BigDecimal,
    val description: String,
    val timestamp: Timestamp,
)