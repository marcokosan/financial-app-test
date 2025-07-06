package com.marcokosan.financialapptest.data.di

import com.marcokosan.financialapptest.data.local.dao.AccountDao
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.repository.AccountRepository
import com.marcokosan.financialapptest.data.repository.AccountRepositoryImpl
import com.marcokosan.financialapptest.data.repository.TransactionsRepository
import com.marcokosan.financialapptest.data.repository.TransactionsRepositoryImpl
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
    fun provideAccountRepository(
        accountDao: AccountDao,
    ): AccountRepository {
        return AccountRepositoryImpl(accountDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionsRepository {
        return TransactionsRepositoryImpl(transactionDao)
    }
}