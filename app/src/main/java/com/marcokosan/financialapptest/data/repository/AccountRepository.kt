package com.marcokosan.financialapptest.data.repository

import com.marcokosan.financialapptest.data.local.dao.AccountDao
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import com.marcokosan.financialapptest.model.Account
import kotlinx.coroutines.delay

interface AccountRepository {
    suspend fun getAccount(accountId: String): Account?
}

class AccountRepositoryImpl(
    private val dao: AccountDao,
) : AccountRepository {

    override suspend fun getAccount(accountId: String): Account? {
        // Simulate IO delay.
        delay(1000)

        return dao.getById(accountId)?.toDomain()
    }
}