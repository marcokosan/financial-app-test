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
        val accountId = "accountId"
        val accountEntity = AccountEntity(
            id = "id",
            holderName = "holderName",
            balance = BigDecimal("1.0"),
        )
        val expectedAccount = accountEntity.toDomain()
        coEvery { dao.getById(accountId) } returns accountEntity

        val result = repository.getAccount(accountId)

        assertEquals(expectedAccount, result)
    }

    @Test
    fun whenNoAccount_returnsNull() = runTest {
        val accountId = "accountId"
        coEvery { dao.getById(accountId) } returns null

        val result = repository.getAccount(accountId)

        assertNull(result)
    }
}