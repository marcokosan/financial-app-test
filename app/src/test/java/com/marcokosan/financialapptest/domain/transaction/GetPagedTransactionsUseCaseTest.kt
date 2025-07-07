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

class GetPagedTransactionsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val transactionsRepository: TransactionRepository = mockk()

    private val getPagedTransactionsUseCase = GetPagedTransactionsUseCase(transactionsRepository)

    @Test
    fun returnPagingDataFlow() = runTest {
        val accountId = "accountId"
        val pageSize = 66
        val enablePlaceholders = true
        val expectedPagingData: PagingData<Transaction> = PagingData.from(listOf())
        val expectedFlow = flowOf(expectedPagingData)

        every {
            transactionsRepository.getPagedTransactions(
                accountId = accountId,
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            )
        } returns expectedFlow

        val resultFlow = getPagedTransactionsUseCase(
            accountId = accountId,
            pageSize = pageSize,
            enablePlaceholders = enablePlaceholders
        )
        val actualPagingData = resultFlow.first()

        verify {
            @Suppress("UnusedFlow")
            transactionsRepository.getPagedTransactions(
                accountId = accountId,
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            )
        }
        assertEquals(expectedPagingData, actualPagingData)
    }
}