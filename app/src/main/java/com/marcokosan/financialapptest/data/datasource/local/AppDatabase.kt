package com.marcokosan.financialapptest.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcokosan.financialapptest.data.datasource.local.converter.BigDecimalConverter
import com.marcokosan.financialapptest.data.datasource.local.converter.TimestampConverter
import com.marcokosan.financialapptest.data.datasource.local.dao.AccountDao
import com.marcokosan.financialapptest.data.datasource.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.datasource.local.entity.AccountEntity
import com.marcokosan.financialapptest.data.datasource.local.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [
        BigDecimalConverter::class,
        TimestampConverter::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}