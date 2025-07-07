package com.marcokosan.financialapptest.data.local.mapper

import com.marcokosan.financialapptest.data.local.entity.TransactionEntity
import com.marcokosan.financialapptest.model.Transaction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.sql.Timestamp

class TransactionMapperTest {

    private val value = BigDecimal(1)
    private val timestamp = Timestamp(1)

    private val entity = TransactionEntity(
        id = 1,
        accountId = "accountId",
        value = value,
        description = "description",
        timestamp = timestamp,
    )

    private val model = Transaction(
        id = 1,
        accountId = "accountId",
        value = value,
        description = "description",
        timestamp = timestamp,
    )

    @Test
    fun entityToDomainModel() {
        assertEquals(model, entity.toDomain())
    }

    @Test
    fun domainModelToEntity() {
        assertEquals(entity, model.toEntity())
    }

    @Test
    fun whenValueIsPositive_returnsIsIncomeEqualsTrue() {
        val transaction = createTransaction(value = BigDecimal(1))
        assertTrue(transaction.isIncome)
    }

    @Test
    fun whenValueIsZero_returnsIsIncomeEqualsTrue() {
        val transaction = createTransaction(value = BigDecimal(0))
        assertTrue(transaction.isIncome)
    }

    @Test
    fun whenValueIsNegative_returnsIsIncomeEqualsFalse() {
        val transaction = createTransaction(value = BigDecimal(-1))
        assertFalse(transaction.isIncome)
    }

    private fun createTransaction(value: BigDecimal) = Transaction(
        id = 1,
        accountId = "accountId",
        value = value,
        description = "description",
        timestamp = timestamp,
    )
}