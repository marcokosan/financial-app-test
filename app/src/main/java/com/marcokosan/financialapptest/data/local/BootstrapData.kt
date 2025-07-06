package com.marcokosan.financialapptest.data.local

import com.marcokosan.financialapptest.data.local.entity.AccountEntity
import com.marcokosan.financialapptest.data.local.entity.TransactionEntity
import java.math.BigDecimal
import java.math.RoundingMode
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random

private const val MOCK_ACCOUNT_ID = "42"

class BootstrapData @Inject constructor(
    private val db: Provider<AppDatabase>,
) {
    suspend fun load() {
        db.get().accountDao().save(
            AccountEntity(
                id = MOCK_ACCOUNT_ID,
                holderName = "Marco",
                balance = BigDecimal("1400050.42")
            ),
        )


        db.get().transactionDao()
            .insert(*generateTransactions().toTypedArray())
    }

    private fun generateTransactions(): List<TransactionEntity> {
        fun randomValue(factor: Int = 1): BigDecimal {
            return BigDecimal(Random.nextDouble(0.0, 1000.0) * factor)
                .setScale(2, RoundingMode.HALF_UP)
        }

        val transactions = listOf(
            List(20) { "Pagamento recebido" to randomValue() },
            List(7) { "Transferência recebida" to randomValue() },
            List(3) { "Transferência enviada" to randomValue(factor = -1) },
        ).flatten()


        fun randomTimestamp(): Timestamp {
            val startDate = SimpleDateFormat("dd/MM/yy", Locale.ROOT).parse("01/01/25")!!
            return Timestamp((startDate.time..Date().time).random())
        }

        return transactions.map {
            TransactionEntity(
                accountId = MOCK_ACCOUNT_ID,
                value = it.second,
                description = it.first,
                timestamp = randomTimestamp(),
            )
        }
    }
}