package com.marcokosan.financialapptest.data.repository

import com.marcokosan.financialapptest.data.local.dao.AccountDao
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import com.marcokosan.financialapptest.model.Account
import kotlinx.coroutines.delay

interface AccountRepository {
    suspend fun getAccount(accountId: String): Account?
}

class AccountRepositoryImpl(
    private val accountDao: AccountDao,
) : AccountRepository {

    override suspend fun getAccount(accountId: String): Account? {
        // Simulate network delay.
        delay(1000)

        return accountDao.getById(accountId)?.toDomain()
    }
}