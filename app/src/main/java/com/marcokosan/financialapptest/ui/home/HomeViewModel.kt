package com.marcokosan.financialapptest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marcokosan.financialapptest.R
import com.marcokosan.financialapptest.data.model.Transaction
import com.marcokosan.financialapptest.data.repository.AccountInfoRepository
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import com.marcokosan.financialapptest.util.CurrencyUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Error(val message: String? = null) : HomeUiState()
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

    // TODO: Get accountId through login flow.
    private val accountId: String = "42"

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    val transactions: Flow<PagingData<Transaction>> =
        accountInfoRepository.getPagedTransactions(accountId)
            .cachedIn(viewModelScope)


    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

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
            accountInfoRepository.getAccount(accountId).fold(
                onSuccess = { data ->
                    _uiState.value = HomeUiState.Success(
                        userName = data.holderName,
                        balance = CurrencyUtils.FORMATTER.format(data.balance),
                    )
                },
                onFailure = {
                    if (_uiState.value is HomeUiState.Success) {
                        _event.send(ScreenEvent.ShowSnackbar(R.string.error_message))
                    } else {
                        _uiState.value = HomeUiState.Error()
                    }
                }
            )
        }
    }
}