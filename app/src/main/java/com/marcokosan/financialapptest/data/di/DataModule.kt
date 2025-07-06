package com.marcokosan.financialapptest.data.di

import com.marcokosan.financialapptest.data.local.dao.AccountDao
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.repository.AccountInfoRepository
import com.marcokosan.financialapptest.data.repository.AccountInfoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAccountInfoRepository(
        accountDao: AccountDao,
        transactionDao: TransactionDao,
    ): AccountInfoRepository {
        return AccountInfoRepositoryImpl(accountDao, transactionDao)
    }
}