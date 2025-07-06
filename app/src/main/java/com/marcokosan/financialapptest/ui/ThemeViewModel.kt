package com.marcokosan.financialapptest.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}