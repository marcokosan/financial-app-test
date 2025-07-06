package com.marcokosan.financialapptest.data.datasource.local.mapper

import com.marcokosan.financialapptest.data.datasource.local.entity.TransactionEntity
import com.marcokosan.financialapptest.data.model.Transaction

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    accountId = accountId,
    value = value,
    description = description,
    timestamp = timestamp,
)

@Suppress("unused")
fun Transaction.toEntity() = TransactionEntity(
    id = id,
    accountId = accountId,
    value = value,
    description = description,
    timestamp = timestamp,
)