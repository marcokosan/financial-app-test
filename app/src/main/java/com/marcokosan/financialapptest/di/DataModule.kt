package com.marcokosan.financialapptest.di

import android.content.Context
import androidx.room.Room
import com.marcokosan.financialapptest.data.repository.AccountInfoRepository
import com.marcokosan.financialapptest.data.repository.AccountInfoRepositoryImpl
import com.marcokosan.financialapptest.data.source.local.AppDatabase
import com.marcokosan.financialapptest.data.source.local.dao.AccountInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideAccountInfoDao(db: AppDatabase): AccountInfoDao = db.accountInfoDao()

    @Provides
    @Singleton
    fun provideAccountInfoRepository(
        dao: AccountInfoDao,
    ): AccountInfoRepository {
        return AccountInfoRepositoryImpl(dao)
    }

}