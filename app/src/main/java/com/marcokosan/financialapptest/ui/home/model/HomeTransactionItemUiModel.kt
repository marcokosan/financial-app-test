package com.marcokosan.financialapptest.ui.home.model

data class HomeTransactionItemUiModel(
    val id: Long,
    val value: String,
    val description: String,
    val date: String,
    val isIncome: Boolean,
)