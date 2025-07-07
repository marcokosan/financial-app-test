package com.marcokosan.financialapptest.data.repository

import com.marcokosan.financialapptest.data.local.dao.AccountDao
import com.marcokosan.financialapptest.data.local.entity.AccountEntity
import com.marcokosan.financialapptest.data.local.mapper.toDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class AccountRepositoryTest {

    private val dao: AccountDao = mockk()
    private val repository = AccountRepositoryImpl(dao)

    @Test
    fun returnAccount() = runTest {
        val id = "id"
        val entity = AccountEntity(
            id = id,
            holderName = "holderName",
            balance = BigDecimal(1),
        )
        val expectedModel = entity.toDomain()
        coEvery { dao.getById(id) } returns entity

        val result = repository.getAccount(id)

        assertEquals(expectedModel, result)
    }

    @Test
    fun whenAccountNotFound_returnsNull() = runTest {
        val id = "id"
        coEvery { dao.getById(id) } returns null

        val result = repository.getAccount(id)

        assertNull(result)
    }
}