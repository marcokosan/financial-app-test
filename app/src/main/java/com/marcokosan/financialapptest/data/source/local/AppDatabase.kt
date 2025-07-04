package com.marcokosan.financialapptest.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcokosan.financialapptest.data.source.local.dao.AccountInfoDao
import com.marcokosan.financialapptest.data.source.local.entity.AccountInfoEntity

@Database(
    entities = [
        AccountInfoEntity::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountInfoDao(): AccountInfoDao
}