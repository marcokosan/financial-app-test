package com.marcokosan.financialapptest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcokosan.financialapptest.data.repository.AccountInfoRepository
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

private val CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    data class Success(
        val isLoading: Boolean = false,
        val userName: String? = null,
        val balance: String? = null,
    ) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountInfoRepository: AccountInfoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    // TODO: Get accountId through login flow.
    private val accountId: String = "42"

    init {
        refreshBalance()
    }

    fun refreshBalance() {
        _uiState.value.let { state ->
            if (state is HomeUiState.Success) {
                _uiState.update {
                    state.copy(isLoading = true)
                }
            } else {
                _uiState.value = HomeUiState.Loading
            }
        }

        viewModelScope.launch {
            delay(2000) // FIXME: Remover.

            accountInfoRepository.getAccountInfo(accountId).fold(
                onSuccess = { data ->
                    _uiState.value = HomeUiState.Success(
                        userName = data.holderName,
                        balance = CURRENCY_FORMATTER.format(data.balance),
                    )
                },
                onFailure = {
                    if (_uiState.value is HomeUiState.Success) {
                        _event.send(ScreenEvent.ShowSnackbar("Ocorreu um erro"))
                    } else {
                        _uiState.value = HomeUiState.Error("Ocorreu um erro.")
                    }
                }
            )
        }
    }
}