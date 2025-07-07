package com.marcokosan.financialapptest.domain.account

import com.marcokosan.financialapptest.data.repository.AccountRepository
import com.marcokosan.financialapptest.model.Account
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountInfoRepository: AccountRepository,
) {
    suspend operator fun invoke(id: String): Result<Account> = runCatching {
        accountInfoRepository.getAccount(id)
            ?: return Result.failure(Exception("Account not found for id $id"))
    }
}