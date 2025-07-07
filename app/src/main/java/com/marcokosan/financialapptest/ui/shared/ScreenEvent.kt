package com.marcokosan.financialapptest.ui.shared

import androidx.annotation.StringRes

sealed class ScreenEvent {
    data class ShowSnackbar(@StringRes val stringResId: Int): ScreenEvent()
}