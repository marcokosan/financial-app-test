package com.marcokosan.financialapptest.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marcokosan.financialapptest.data.source.local.entity.AccountInfoEntity

@Dao
interface AccountInfoDao {
    @Query("SELECT * FROM balance WHERE accountId = :accountId")
    suspend fun getAccountInfo(accountId: String): AccountInfoEntity?

    @Upsert
    suspend fun save(account: AccountInfoEntity)
}