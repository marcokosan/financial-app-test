package com.marcokosan.financialapptest.util

import java.text.NumberFormat
import java.util.Locale

object Utils {

    val LOCALE_PT_BR: Locale = Locale.forLanguageTag("pt-BR")

    val CURRENCY_FORMATTER: NumberFormat = NumberFormat.getCurrencyInstance(LOCALE_PT_BR)
}