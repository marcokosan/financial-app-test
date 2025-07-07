package com.marcokosan.financialapptest.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.marcokosan.financialapptest.data.local.AppDatabase
import com.marcokosan.financialapptest.data.local.FakeData
import com.marcokosan.financialapptest.data.local.dao.AccountDao
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAccountDao(db: AppDatabase): AccountDao =
        db.accountDao()

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao =
        db.transactionDao()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
        fakeData: FakeData,
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    CoroutineScope(Dispatchers.IO).launch {
                        fakeData.generate()
                    }
                }
            })
            .build()
    }
}