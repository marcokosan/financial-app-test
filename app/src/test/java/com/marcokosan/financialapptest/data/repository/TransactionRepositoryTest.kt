package com.marcokosan.financialapptest.data.repository

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.marcokosan.financialapptest.data.local.dao.TransactionDao
import com.marcokosan.financialapptest.data.local.entity.TransactionEntity
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
        val transactionsList = List(10) { index -> fakeEntity(id = index.toLong()) }
        val expectedTransactionsList = transactionsList.map { it.toDomain() }
        every { dao.getPagingSource(accountId) } returns transactionsList
            .asPagingSourceFactory().invoke()

        val result = repository.getPagedTransactions(accountId, pageSize, enablePlaceholders)

        val snapshot = result.asSnapshot()
        assertEquals(expectedTransactionsList, snapshot)
    }

    private fun fakeEntity(id: Long): TransactionEntity {
        return TransactionEntity(
            id = id,
            accountId = "accountId",
            value = BigDecimal(id),
            description = "Desc $id",
            timestamp = Timestamp(0)
        )
    }
}