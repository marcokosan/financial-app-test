package com.marcokosan.financialapptest.data.source.local.mapper

import com.marcokosan.financialapptest.data.model.AccountInfo
import com.marcokosan.financialapptest.data.source.local.entity.AccountInfoEntity

fun AccountInfoEntity.toDomain() = AccountInfo(
    accountId = accountId,
    holderName = holderName,
    balance = balance
)

fun AccountInfo.toEntity() = AccountInfoEntity(
    accountId = accountId,
    holderName = holderName,
    balance = balance
)