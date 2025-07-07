package com.marcokosan.financialapptest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import com.marcokosan.financialapptest.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TransactionRepository {
    fun getPagedTransactions(
        accountId: String,
        pageSize: Int,
        enablePlaceholders: Boolean,
    ): Flow<PagingData<Transaction>>
}

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
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
            pagingSourceFactory = { transactionDao.getPagingSource(accountId) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}