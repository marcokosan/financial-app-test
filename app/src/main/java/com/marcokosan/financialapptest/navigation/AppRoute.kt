package com.marcokosan.financialapptest.navigation

import kotlinx.serialization.Serializable

object AppRoute {
    @Serializable
    object Home

    @Serializable
    data class TransactionDetail(val transactionId: Long)
}
