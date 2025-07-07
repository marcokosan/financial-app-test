package com.marcokosan.financialapptest.domain.transaction

import androidx.paging.PagingData
import com.marcokosan.financialapptest.data.repository.TransactionRepository
import com.marcokosan.financialapptest.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(
        accountId: String,
        pageSize: Int = 20,
        enablePlaceholders: Boolean = false,
    ): Flow<PagingData<Transaction>> =
        transactionRepository.getPagedTransactions(accountId, pageSize, enablePlaceholders)
}