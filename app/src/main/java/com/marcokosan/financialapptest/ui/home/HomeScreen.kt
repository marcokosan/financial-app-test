package com.marcokosan.financialapptest.ui.home

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.marcokosan.financialapptest.R
import com.marcokosan.financialapptest.designsystem.component.DsErrorMessage
import com.marcokosan.financialapptest.designsystem.theme.DesignSystemTheme
import com.marcokosan.financialapptest.designsystem.theme.extendedColorScheme
import com.marcokosan.financialapptest.model.Transaction
import com.marcokosan.financialapptest.ui.ThemeViewModel
import com.marcokosan.financialapptest.ui.shared.ScreenEvent
import com.marcokosan.financialapptest.util.Utils
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_PATTERN = "dd MMM"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onTransactionClick: (Long) -> Unit,
) {
    val activity = LocalActivity.current as ComponentActivity
    val themeViewModel: ThemeViewModel = hiltViewModel(activity)

    val uiState by viewModel.uiState.collectAsState()
    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

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
                    snackbarHostState.showSnackbar(context.getString(event.stringResId))
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = colorScheme.primary,
                    actionIconContentColor = colorScheme.onPrimary
                ),
                title = {
                    Balance(
                        modifier = Modifier.padding(end = 16.dp),
                        value = (uiState as? HomeUiState.Success)?.balance ?: "-",
                    )
                },
                actions = {
                    val isDarkTheme = themeViewModel.isDarkTheme.value

                    IconButton(onClick = themeViewModel::toggleTheme) {
                        Icon(
                            imageVector = if (isDarkTheme) {
                                Icons.Default.LightMode
                            } else {
                                Icons.Default.DarkMode
                            },
                            contentDescription =
                                stringResource(R.string.home_switch_theme_mode_description),
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        val state = uiState
        when (state) {
            is HomeUiState.Success -> {
                Content(
                    contentPadding = innerPadding,
                    transactions = transactions,
                    isRefreshing = state.isRefreshing,
                    onRefresh = {
                        viewModel.refreshUi()
                        transactions.refresh()
                    },
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
                DsErrorMessage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    message = state.message,
                    onTryAgain = {
                        viewModel.refreshUi()
                        transactions.refresh()
                    }
                )
            }
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
            text = stringResource(R.string.balance),
            style = typography.headlineSmall,
            color = colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = value,
            style = typography.displaySmall,
            color = colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    transactions: LazyPagingItems<Transaction>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onTransactionClick: (Long) -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier.padding(top = contentPadding.calculateTopPadding()),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(transactions.itemCount) { index ->
                transactions[index]?.let { transaction ->
                    TransactionItem(
                        item = transaction,
                        modifier = Modifier.clickable {
                            onTransactionClick(transaction.id)
                        }
                    )
                }
            }


            when (transactions.loadState.append) {
                is LoadState.Error -> item {
                    IconButton(onClick = transactions::retry) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.refresh)
                        )
                    }
                }

                LoadState.Loading -> item { CircularProgressIndicator() }
                is LoadState.NotLoading -> {
                    if (transactions.itemCount == 0) {
                        item {
                            Text(
                                stringResource(R.string.home_statement_empty),
                                modifier = Modifier.padding(all = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(item: Transaction, modifier: Modifier = Modifier) {
    val transactionTypeColor = if (item.isIncome) {
        extendedColorScheme.success
    } else {
        colorScheme.onSurface
    }

    val dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

    ListItem(
        modifier = modifier,
        leadingContent = {
            Icon(
                imageVector = if (item.isIncome) {
                    Icons.Default.ArrowCircleDown
                } else {
                    Icons.Default.ArrowCircleUp
                },
                tint = transactionTypeColor,
                contentDescription = null,
            )
        },
        headlineContent = { Text(item.description) },
        supportingContent = {
            Text(
                Utils.CURRENCY_FORMATTER.format(item.value),
                color = transactionTypeColor,
            )
        },
        trailingContent = { Text(dateFormat.format(item.timestamp)) }
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    DesignSystemTheme {
        HomeScreen(onTransactionClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun BalancePreview() {
    DesignSystemTheme {
        Balance("R$ 1.400.050,42")
    }
}