package com.marcokosan.financialapptest.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    val FORMATTER: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
}