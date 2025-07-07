package com.marcokosan.financialapptest.domain.transaction

import com.marcokosan.financialapptest.data.repository.TransactionRepository
import com.marcokosan.financialapptest.model.Transaction
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val accountInfoRepository: TransactionRepository,
) {
    suspend operator fun invoke(id: Long): Result<Transaction> = runCatching {
        accountInfoRepository.getTransaction(id)
            ?: return Result.failure(Exception("Transaction with ID $id not found"))
    }
}