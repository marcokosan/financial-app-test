package com.marcokosan.financialapptest.domain.account

import com.marcokosan.financialapptest.data.repository.AccountRepository
import com.marcokosan.financialapptest.model.Account
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class GetAccountUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val accountRepositoryMock: AccountRepository = mockk()

    private val getAccountUseCase = GetAccountUseCase(accountRepositoryMock)

    @Test
    fun returnSuccess() = runTest {
        val accountId = "accountId"
        val expectedAccount = Account(
            id = accountId,
            holderName = "holderName",
            balance = BigDecimal(1),
        )
        coEvery { accountRepositoryMock.getAccount(accountId) } returns expectedAccount

        val result = getAccountUseCase(accountId)

        assertEquals(expectedAccount, result.getOrNull())
    }

    @Test
    fun whenNoAccount_returnsFailure() = runTest {
        val accountId = "accountId"
        coEvery { accountRepositoryMock.getAccount(accountId) } returns null

        val result = getAccountUseCase(accountId)

        assertTrue(result.isFailure)
    }

    @Test
    fun whenExceptionOccurs_returnFailure() = runTest {
        val accountId = "accountId"
        val expectedException = RuntimeException("Database error")
        coEvery { accountRepositoryMock.getAccount(accountId) } throws expectedException

        val result = getAccountUseCase(accountId)

        assertTrue(result.isFailure)
    }
}
