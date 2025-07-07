package com.marcokosan.financialapptest.data.local.mapper

import com.marcokosan.financialapptest.data.local.entity.AccountEntity
import com.marcokosan.financialapptest.model.Account
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class AccountMapperTest {

    private val entity = AccountEntity(
        id = "id",
        holderName = "holderName",
        balance = BigDecimal(1)
    )

    private val model = Account(
        id = "id",
        holderName = "holderName",
        balance = BigDecimal(1)
    )

    @Test
    fun entityToDomainModel() {
        assertEquals(model, entity.toDomain())
    }

    @Test
    fun domainModelToEntity() {
        assertEquals(entity, model.toEntity())
    }
}