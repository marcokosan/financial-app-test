package com.marcokosan.financialapptest.domain.transaction

import androidx.paging.PagingData
import com.marcokosan.financialapptest.data.repository.TransactionsRepository
import com.marcokosan.financialapptest.model.Transaction
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetTransactionsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockTransactionsRepository: TransactionsRepository

    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Before
    fun setUp() {
        getTransactionsUseCase = GetTransactionsUseCase(mockTransactionsRepository)
    }

    @Test
    fun returnPagingDataFlow() = runTest {
        val accountId = "accountId"
        val pageSize = 66
        val enablePlaceholders = true
        val expectedPagingData: PagingData<Transaction> = PagingData.from(listOf())
        val expectedFlow = flowOf(expectedPagingData)

        every {
            mockTransactionsRepository.getPagedTransactions(
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
            mockTransactionsRepository.getPagedTransactions(
                accountId = accountId,
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            )
        }
        assertEquals(expectedPagingData, actualPagingData)
    }
}