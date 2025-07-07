package com.marcokosan.financialapptest.ui.transactiondetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcokosan.financialapptest.R
import com.marcokosan.financialapptest.designsystem.component.DsErrorMessage
import com.marcokosan.financialapptest.designsystem.theme.DesignSystemTheme
import com.marcokosan.financialapptest.designsystem.theme.extendedColorScheme
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_PATTERN = "EEEE, dd 'de' MMMM 'de' yyyy, HH:mm"

@Composable
fun TransactionDetailScreen(
    viewModel: TransactionDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    transactionId: Long,
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is ScreenEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(context.getString(event.stringResId))
                }
            }
        }
    }
    LaunchedEffect(transactionId) {
        viewModel.loadUi(transactionId = transactionId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_transaction_detail)) },
                navigationIcon = {
                    IconButton(onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        val state = uiState
        when (state) {
            is TransactionDetailUiState.Success -> {
                Content(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    data = state,
                )
            }

            is TransactionDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is TransactionDetailUiState.Error -> {
                DsErrorMessage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = state.message,
                    onTryAgain = { viewModel.loadUi(transactionId) }
                )
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    data: TransactionDetailUiState.Success,
) {
    val dateFormat = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy, HH:mm", Locale.getDefault())
    val icon = if (data.isIncome) Icons.Default.ArrowCircleDown else Icons.Default.ArrowCircleUp

    val iconColor = if (data.isIncome) extendedColorScheme.success else colorScheme.onSurface

    Column(modifier = modifier) {
        Icon(
            modifier = Modifier.size(56.dp),
            imageVector = icon,
            tint = iconColor,
            contentDescription = null,
        )
        Text(
            data.description,
            modifier = Modifier.padding(top = 12.dp),
            style = typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            data.value,
            modifier = Modifier.padding(top = 12.dp),
            style = typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            dateFormat.format(data.date),
            modifier = Modifier.padding(top = 12.dp),
            color = colorScheme.onSurfaceVariant,
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    DesignSystemTheme {
        TransactionDetailScreen(
            onBackPressed = {},
            transactionId = 1
        )
    }
}