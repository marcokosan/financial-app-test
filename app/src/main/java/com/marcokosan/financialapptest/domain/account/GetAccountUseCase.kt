package com.marcokosan.financialapptest.domain.account

import com.marcokosan.financialapptest.data.repository.AccountRepository
import com.marcokosan.financialapptest.model.Account
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(id: String): Result<Account> = runCatching {
        accountRepository.getAccount(id)
            ?: return Result.failure(Exception("Account with ID $id not found"))
    }
}