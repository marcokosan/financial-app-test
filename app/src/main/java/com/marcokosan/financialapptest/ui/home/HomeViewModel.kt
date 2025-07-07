package com.marcokosan.financialapptest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.marcokosan.financialapptest.R
import com.marcokosan.financialapptest.domain.account.GetAccountUseCase
import com.marcokosan.financialapptest.domain.transaction.GetPagedTransactionsUseCase
import com.marcokosan.financialapptest.ui.home.mapper.toHomeTransactionItemUiModel
import com.marcokosan.financialapptest.ui.home.model.HomeTransactionItemUiModel
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import com.marcokosan.financialapptest.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Error(val message: String? = null) : HomeUiState()
    data class Success(
        val isRefreshing: Boolean = false,
        val userName: String? = null,
        val balance: String? = null,
    ) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val geAccount: GetAccountUseCase,
    getPagedTransactions: GetPagedTransactionsUseCase,
) : ViewModel() {

    // TODO: Get accountId through login flow.
    private val accountId: String = "42"

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    val transactions: Flow<PagingData<HomeTransactionItemUiModel>> =
        getPagedTransactions(accountId = accountId)
            .map { pagingData ->
                pagingData.map { it.toHomeTransactionItemUiModel() }
            }
            .cachedIn(viewModelScope)

    init {
        refreshUi()
    }

    fun refreshUi() {
        _uiState.value.let { state ->
            if (state is HomeUiState.Success) {
                _uiState.value = state.copy(isRefreshing = true)
            } else {
                _uiState.value = HomeUiState.Loading
            }
        }

        viewModelScope.launch {
            geAccount(accountId).fold(
                onSuccess = { data ->
                    _uiState.value = HomeUiState.Success(
                        userName = data.holderName,
                        balance = Utils.CURRENCY_FORMATTER.format(data.balance),
                    )
                },
                onFailure = {
                    val currentState = _uiState.value
                    if (currentState is HomeUiState.Success) {
                        _uiState.value = currentState.copy(isRefreshing = false)
                        _event.send(ScreenEvent.ShowSnackbar(R.string.error_message))
                    } else {
                        _uiState.value = HomeUiState.Error()
                    }
                }
            )
        }
    }
}