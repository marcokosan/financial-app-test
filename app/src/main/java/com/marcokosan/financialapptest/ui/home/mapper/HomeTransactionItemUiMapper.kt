package com.marcokosan.financialapptest.ui.home.mapper

import com.marcokosan.financialapptest.model.Transaction
import com.marcokosan.financialapptest.ui.home.model.HomeTransactionItemUiModel
import com.marcokosan.financialapptest.util.Utils
import java.text.SimpleDateFormat

private val DATE_FORMATTER = SimpleDateFormat("dd MMM", Utils.LOCALE_PT_BR)

fun Transaction.toHomeTransactionItemUiModel() = HomeTransactionItemUiModel(
    id = id,
    value = Utils.CURRENCY_FORMATTER.format(value),
    description = description,
    date = DATE_FORMATTER.format(timestamp),
    isIncome = value.signum() >= 0
)