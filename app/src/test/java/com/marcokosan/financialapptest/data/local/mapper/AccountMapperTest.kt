package com.marcokosan.financialapptest.data.local.mapper

import com.marcokosan.financialapptest.data.local.entity.AccountEntity
import com.marcokosan.financialapptest.model.Account
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class AccountMapperTest {

    @Test
    fun domainMapper() {
        val entity = AccountEntity(
            id = "id",
            holderName = "holderName",
            balance = BigDecimal(1)
        )
        val expected = Account(
            id = "id",
            holderName = "holderName",
            balance = BigDecimal(1)
        )

        val result = entity.toDomain()

        assertEquals(expected, result)
    }

    @Test
    fun entityMapper() {
        val model = Account(
            id = "id",
            holderName = "holderName",
            balance = BigDecimal(1)
        )
        val expected = AccountEntity(
            id = "id",
            holderName = "holderName",
            balance = BigDecimal(1)
        )

        val result = model.toEntity()

        assertEquals(expected, result)
    }
}