package com.marcokosan.financialapptest.ui.home

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import com.marcokosan.financialapptest.ui.theme.DesignSystemTheme
import com.marcokosan.financialapptest.ui.theme.extendedColorScheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val TRANSACTION_DATE_FORMAT = SimpleDateFormat("dd MMM", Locale.forLanguageTag("pt-BR"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onTransactionClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val view = LocalView.current
    val context = LocalContext.current

    SideEffect {
        val window = (context as Activity).window
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = false
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is ScreenEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Balance(
                        modifier = Modifier.padding(end = 16.dp),
                        value = (uiState as? HomeUiState.Success)?.balance ?: "-",
                    )
                },
            )
        }
    ) { innerPadding ->
        val state = uiState
        when (state) {
            is HomeUiState.Success -> {
                Content(
                    contentPadding = innerPadding,
                    onTransactionClick = onTransactionClick,
                )
            }

            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is HomeUiState.Error -> {
                ErrorMessage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = state.message,
                    onTryAgain = {
                        viewModel.refreshBalance()
                        scope.launch {
                            snackbarHostState.showSnackbar("Test")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    onTransactionClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        val items = List(50) { index -> "Item ${index + 1}" }
        items(items.size) { index ->
            val transactionId = items[index]
            TransactionItem(
                item = Date(),
                modifier = Modifier.clickable {
                    onTransactionClick(transactionId)
                }
            )
        }
    }
}


@Composable
private fun Balance(value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Saldo",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun TransactionItem(item: Date, modifier: Modifier = Modifier) {
    val date = TRANSACTION_DATE_FORMAT.format(item)
    ListItem(
        modifier = modifier,
        leadingContent = {
            Icon(
                imageVector = Icons.Default.ArrowCircleDown,
                contentDescription = "Ícone de transação"
            )
        },
        headlineContent = { Text("Transação") },
        supportingContent = {
            Text("R$ 10.020,00", color = extendedColorScheme.success)
        },
        trailingContent = { Text(date) }
    )
}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onTryAgain: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onTryAgain) {
            // TODO: Strings xml.
            Text("Tentar novamente")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    DesignSystemTheme {
        HomeScreen(onTransactionClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun BalancePreview() {
    DesignSystemTheme {
        Balance("R$ 123.456,78")
    }
}