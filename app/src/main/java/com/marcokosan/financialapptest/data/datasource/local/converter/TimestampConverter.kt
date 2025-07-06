package com.marcokosan.financialapptest.data.datasource.local.converter

import androidx.room.TypeConverter
import java.sql.Timestamp

class TimestampConverter {

    @TypeConverter
    fun fromLong(value: Long?): Timestamp? {
        return value?.let { Timestamp(it) }
    }

    @TypeConverter
    fun toLong(timestamp: Timestamp?): Long? {
        return timestamp?.time
    }
}