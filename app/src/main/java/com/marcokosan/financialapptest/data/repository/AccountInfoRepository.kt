package com.marcokosan.financialapptest.data.repository

import com.marcokosan.financialapptest.data.model.AccountInfo
import com.marcokosan.financialapptest.data.source.local.dao.AccountInfoDao
import com.marcokosan.financialapptest.data.source.local.entity.AccountInfoEntity
import com.marcokosan.financialapptest.data.source.local.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

interface AccountInfoRepository {
    suspend fun getAccountInfo(accountId: String): Result<AccountInfo>
    suspend fun getAccountStatement(accountId: String, page: Int? = null, size: Int? = null)

    // FIXME: Remover.
    suspend fun mockData()
}

class AccountInfoRepositoryImpl(
    private val accountInfoDao: AccountInfoDao,
) : AccountInfoRepository {

    override suspend fun getAccountInfo(accountId: String): Result<AccountInfo> =
        withContext(Dispatchers.IO) {
            accountInfoDao.getAccountInfo(accountId)
                ?.let { Result.success(it.toDomain()) }
                ?: Result.failure(Error("AccountId not found"))
        }

    override suspend fun getAccountStatement(accountId: String, page: Int?, size: Int?) {
    }

    override suspend fun mockData() {
        accountInfoDao.save(
            AccountInfoEntity(
                accountId = "42",
                holderName = "Marco",
                balance = BigDecimal("1400050.42")
            ),
        )
    }
}