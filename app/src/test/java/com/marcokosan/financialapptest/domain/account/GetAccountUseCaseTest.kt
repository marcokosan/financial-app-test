package com.marcokosan.financialapptest.domain.account

import com.marcokosan.financialapptest.data.repository.AccountRepository
import com.marcokosan.financialapptest.model.Account
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class GetAccountUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockAccountRepository: AccountRepository

    private lateinit var getAccountUseCase: GetAccountUseCase

    @Before
    fun setup() {
        getAccountUseCase = GetAccountUseCase(mockAccountRepository)
    }

    @Test
    fun whenAccountExists_returnSuccess() = runTest {
        val accountId = "accountId"
        val expectedAccount = Account(
            id = accountId,
            holderName = "holderName",
            balance = BigDecimal("1.00"),
        )
        coEvery { mockAccountRepository.getAccount(accountId) } returns expectedAccount

        val result = getAccountUseCase(accountId)

        assertEquals(expectedAccount, result.getOrNull())
    }

    @Test
    fun whenNoAccount_returnFailure() = runTest {
        val accountId = "accountId"
        coEvery { mockAccountRepository.getAccount(accountId) } returns null

        val result = getAccountUseCase(accountId)

        assertTrue(result.isFailure)
    }

    @Test
    fun whenExceptionOccurs_returnFailure() = runTest {
        val accountId = "accountId"
        val expectedException = RuntimeException("Database error")
        coEvery { mockAccountRepository.getAccount(accountId) } throws expectedException

        val result = getAccountUseCase(accountId)

        assertTrue(result.isFailure)
    }
}
