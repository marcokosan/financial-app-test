package com.marcokosan.financialapptest.data.repository

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.local.entity.TransactionEntity
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertNull
import org.junit.Test
import java.math.BigDecimal
import java.sql.Timestamp

@ExperimentalCoroutinesApi
class TransactionRepositoryTest {

    private val dao: TransactionDao = mockk()
    private val repository = TransactionRepositoryImpl(dao)

    @Test
    fun whenGetPagedTransactionsIsCalled_returnsMappedTransactions() = runTest {
        val accountId = "accountId"
        val pageSize = 10
        val enablePlaceholders = true
        val transactions = List(10) { index ->
            createTransactionEntity(id = index.toLong())
        }
        val expectedTransactionsList = transactions.map { it.toDomain() }
        every { dao.getPagingSource(accountId) } returns transactions
            .asPagingSourceFactory().invoke()

        val result = repository.getPagedTransactions(accountId, pageSize, enablePlaceholders)

        val snapshot = result.asSnapshot()
        assertEquals(expectedTransactionsList, snapshot)
    }

    @Test
    fun returnTransaction() = runTest {
        val id = 1L
        val entity = createTransactionEntity()
        val expectedModel = entity.toDomain()
        coEvery { dao.getById(id) } returns entity

        val result = repository.getTransaction(id)

        Assert.assertEquals(expectedModel, result)
    }

    @Test
    fun whenTransactiontNotFound_returnsNull() = runTest {
        val id = 1L
        coEvery { dao.getById(id) } returns null

        val result = repository.getTransaction(id)

        assertNull(result)
    }

    private fun createTransactionEntity(
        id: Long = 1,
    ) = TransactionEntity(
        id = id,
        accountId = "accountId",
        value = BigDecimal(1),
        description = "description $id",
        timestamp = Timestamp(1)
    )
}