package com.marcokosan.financialapptest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.marcokosan.financialapptest.data.datasource.local.dao.AccountDao
import com.marcokosan.financialapptest.data.datasource.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.datasource.local.mapper.toDomain
import com.marcokosan.financialapptest.data.model.Account
import com.marcokosan.financialapptest.data.model.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AccountInfoRepository {
    suspend fun getAccount(accountId: String): Result<Account>
    fun getPagedTransactions(
        accountId: String,
        pageSize: Int = 20,
        enablePlaceholders: Boolean = false,
    ): Flow<PagingData<Transaction>>
}

class AccountInfoRepositoryImpl(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao,
) : AccountInfoRepository {

    override suspend fun getAccount(accountId: String): Result<Account> {
        // Simulate network delay.
        delay(200)

        return accountDao.getById(accountId)
            ?.let { Result.success(it.toDomain()) }
            ?: Result.failure(Error("Account not found: id $accountId"))
    }

    override fun getPagedTransactions(
        accountId: String,
        pageSize: Int,
        enablePlaceholders: Boolean,
    ): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            ),
            pagingSourceFactory = { transactionDao.getPagingSource(accountId) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}