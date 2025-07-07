package com.marcokosan.financialapptest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import com.marcokosan.financialapptest.model.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TransactionRepository {
    fun getPagedTransactions(
        accountId: String,
        pageSize: Int,
        enablePlaceholders: Boolean,
    ): Flow<PagingData<Transaction>>

    suspend fun getTransaction(
        id: Long,
    ): Transaction?
}

class TransactionRepositoryImpl(
    private val dao: TransactionDao,
) : TransactionRepository {

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
            pagingSourceFactory = { dao.getPagingSource(accountId) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getTransaction(id: Long): Transaction? {
        // Simulate IO delay.
        delay(1000)

        return dao.getById(id)?.toDomain()
    }
}