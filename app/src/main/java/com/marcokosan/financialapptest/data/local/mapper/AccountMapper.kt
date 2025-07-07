package com.marcokosan.financialapptest.data.local.mapper

import com.marcokosan.financialapptest.data.local.entity.AccountEntity
import com.marcokosan.financialapptest.model.Account

fun AccountEntity.toDomain() = Account(
    id = id,
    holderName = holderName,
    balance = balance
)

fun Account.toEntity() = AccountEntity(
    id = id,
    holderName = holderName,
    balance = balance
)