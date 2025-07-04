package com.marcokosan.financialapptest.ui.shared

sealed class ScreenEvent {
    data class ShowSnackbar(val message: String): ScreenEvent()
}