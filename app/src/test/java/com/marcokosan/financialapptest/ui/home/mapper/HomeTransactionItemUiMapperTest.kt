package com.marcokosan.financialapptest.ui.home.mapper

import com.marcokosan.financialapptest.model.Transaction
import com.marcokosan.financialapptest.ui.home.model.HomeTransactionItemUiModel
import com.marcokosan.financialapptest.util.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.sql.Timestamp

class HomeTransactionItemUiMapperTest {

    private val timestamp = Timestamp(0)
    private val formattedTimestamp = "31 dez."

    @Test
    fun whenPositiveValue_returnsIsIncomeTrue() {
        val value = BigDecimal(1)
        val model = Transaction(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )
        val expected = HomeTransactionItemUiModel(
            id = 1,
            value = Utils.CURRENCY_FORMATTER.format(value),
            description = "description",
            date = formattedTimestamp,
            isIncome = true,
        )

        val result = model.toHomeTransactionItemUiModel()

        assertEquals(expected, result)
    }

    @Test
    fun whenNegativeValue_returnsIsIncomeFalse() {
        val value = BigDecimal(-1)
        val model = Transaction(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )
        val expected = HomeTransactionItemUiModel(
            id = 1,
            value = Utils.CURRENCY_FORMATTER.format(value),
            description = "description",
            date = formattedTimestamp,
            isIncome = false,
        )

        val result = model.toHomeTransactionItemUiModel()

        assertEquals(expected, result)
    }

    @Test
    fun whenValueIsZero_returnsIsIncomeTrue() {
        val value = BigDecimal(0)
        val model = Transaction(
            id = 1,
            accountId = "accountId",
            value = value,
            description = "description",
            timestamp = timestamp,
        )
        val expected = HomeTransactionItemUiModel(
            id = 1,
            value = Utils.CURRENCY_FORMATTER.format(value),
            description = "description",
            date = formattedTimestamp,
            isIncome = true,
        )

        val result = model.toHomeTransactionItemUiModel()

        assertEquals(expected, result)
    }
}