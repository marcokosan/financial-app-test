package com.marcokosan.financialapptest.data.datasource.local.mapper

import com.marcokosan.financialapptest.data.datasource.local.entity.AccountEntity
import com.marcokosan.financialapptest.data.model.Account

fun AccountEntity.toDomain() = Account(
    id = id,
    holderName = holderName,
    balance = balance
)

@Suppress("unused")
fun Account.toEntity() = AccountEntity(
    id = id,
    holderName = holderName,
    balance = balance
)