package com.marcokosan.financialapptest.data.local.mapper

import com.marcokosan.financialapptest.data.local.entity.TransactionEntity
import com.marcokosan.financialapptest.model.Transaction

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    accountId = accountId,
    value = value,
    description = description,
    timestamp = timestamp,
)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    accountId = accountId,
    value = value,
    description = description,
    timestamp = timestamp,
)