package com.marcokosan.financialapptest.domain.transaction

import androidx.paging.PagingData
import com.marcokosan.financialapptest.data.repository.TransactionsRepository
import com.marcokosan.financialapptest.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    operator fun invoke(
        id: String,
        pageSize: Int = 20,
        enablePlaceholders: Boolean = false,
    ): Flow<PagingData<Transaction>> =
        transactionsRepository.getPagedTransactions(id, pageSize, enablePlaceholders)
}