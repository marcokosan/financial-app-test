package com.marcokosan.financialapptest.domain.transaction

import com.marcokosan.financialapptest.data.repository.TransactionRepository
import com.marcokosan.financialapptest.model.Transaction
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import java.sql.Timestamp

class GetTransactionUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val transactionRepository: TransactionRepository = mockk()

    private val getTransactionUseCase = GetTransactionUseCase(transactionRepository)

    @Test
    fun returnTransaction() = runTest {
        val transactionId = 1L
        val expectedTransaction = Transaction(
            id = transactionId,
            accountId = "accountId",
            value = BigDecimal(1),
            description = "description",
            timestamp = Timestamp(1)
        )
        coEvery { transactionRepository.getTransaction(transactionId) } returns expectedTransaction

        val result = getTransactionUseCase(transactionId)

        assertEquals(expectedTransaction, result.getOrNull())
    }

    @Test
    fun whenAccountNotFound_returnsFailure() = runTest {
        val transactionId = 1L
        coEvery { transactionRepository.getTransaction(transactionId) } returns null

        val result = getTransactionUseCase(transactionId)

        assertTrue(result.isFailure)
    }

    @Test
    fun whenExceptionOccurs_returnFailure() = runTest {
        val transactionId = 1L
        val expectedException = RuntimeException("Database error")
        coEvery { transactionRepository.getTransaction(transactionId) } throws expectedException

        val result = getTransactionUseCase(transactionId)

        assertTrue(result.isFailure)
    }
}
