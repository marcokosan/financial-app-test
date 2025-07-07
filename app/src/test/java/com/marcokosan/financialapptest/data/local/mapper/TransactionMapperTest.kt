package com.marcokosan.financialapptest.data.local.mapper

import com.marcokosan.financialapptest.data.local.entity.TransactionEntity
import com.marcokosan.financialapptest.model.Transaction
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.sql.Timestamp

class TransactionMapperTest {

    private val value = BigDecimal(1)
    private val timestamp = Timestamp(0)

    @Test
    fun domainMapper() {
        val entity = TransactionEntity(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )
        val expected = Transaction(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )

        val result = entity.toDomain()

        assertEquals(expected, result)
    }

    @Test
    fun entityMapper() {
        val model = Transaction(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )
        val expected = TransactionEntity(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )

        val result = model.toEntity()

        assertEquals(expected, result)
    }
}