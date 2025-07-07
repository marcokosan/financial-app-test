package com.marcokosan.financialapptest.ui.transactiondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcokosan.financialapptest.domain.transaction.GetTransactionUseCase
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import com.marcokosan.financialapptest.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

sealed class TransactionDetailUiState {
    object Loading : TransactionDetailUiState()
    data class Error(val message: String? = null) : TransactionDetailUiState()
    data class Success(
        val description: String,
        val value: String,
        val date: Date,
        val isIncome: Boolean,
    ) : TransactionDetailUiState()
}

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getTransaction: GetTransactionUseCase,
) : ViewModel() {

    private val _event = Channel<ScreenEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState =
        MutableStateFlow<TransactionDetailUiState>(TransactionDetailUiState.Loading)
    val uiState: StateFlow<TransactionDetailUiState> = _uiState

    fun loadUi(transactionId: Long) {
        _uiState.value = TransactionDetailUiState.Loading

        viewModelScope.launch {
            getTransaction(transactionId).fold(
                onSuccess = { data ->
                    _uiState.value = TransactionDetailUiState.Success(
                        description = data.description,
                        value = Utils.CURRENCY_FORMATTER.format(data.value),
                        date = data.timestamp,
                        isIncome = data.isIncome,
                    )
                },
                onFailure = {
                    _uiState.value = TransactionDetailUiState.Error()
                }
            )
        }
    }
}