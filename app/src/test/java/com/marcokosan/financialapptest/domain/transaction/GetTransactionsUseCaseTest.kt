package com.marcokosan.financialapptest.domain.transaction

import androidx.paging.PagingData
import com.marcokosan.financialapptest.data.repository.TransactionRepository
import com.marcokosan.financialapptest.model.Transaction
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GetTransactionsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val transactionsRepositoryMock: TransactionRepository = mockk()

    private val getTransactionsUseCase = GetTransactionsUseCase(transactionsRepositoryMock)

    @Test
    fun returnPagingDataFlow() = runTest {
        val accountId = "accountId"
        val pageSize = 66
        val enablePlaceholders = true
        val expectedPagingData: PagingData<Transaction> = PagingData.from(listOf())
        val expectedFlow = flowOf(expectedPagingData)

        every {
            transactionsRepositoryMock.getPagedTransactions(
                accountId = accountId,
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            )
        } returns expectedFlow

        val resultFlow = getTransactionsUseCase(
            accountId = accountId,
            pageSize = pageSize,
            enablePlaceholders = enablePlaceholders
        )
        val actualPagingData = resultFlow.first()

        verify {
            @Suppress("UnusedFlow")
            transactionsRepositoryMock.getPagedTransactions(
                accountId = accountId,
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            )
        }
        assertEquals(expectedPagingData, actualPagingData)
    }
}