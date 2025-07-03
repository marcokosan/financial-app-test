package com.marcokosan.financialapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.marcokosan.financialapptest.ui.theme.DesignSystemTheme
import com.marcokosan.financialapptest.ui.theme.extendedColorScheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val TRANSACTION_DATE_FORMAT = SimpleDateFormat("dd MMM", Locale.forLanguageTag("pt-BR"))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DesignSystemTheme {
                val view = LocalView.current
                SideEffect {
                    WindowCompat.getInsetsController(window, view)
                        .isAppearanceLightStatusBars = false
                }

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                    topBar = {
                        LargeTopAppBar(
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = colorScheme.primary,
                            ),
                            title = {
                                Balance(
                                    modifier = Modifier.padding(end = 16.dp),
                                    value = "R$ 1.000.050,42",
                                )
                            },
                        )
                    },
                ) { innerPadding ->
                    LazyColumn(
                        contentPadding = innerPadding
                    ) {
                        val items = List(50) { index -> "Item ${index + 1}" }
                        items(items.size) { index ->
                            val item = items[index]
                            TransactionItem(
                                item = Date(),
                                modifier = Modifier.clickable(
                                    onClick = {
                                        scope.launch {
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarHostState.showSnackbar(item)
                                        }
                                    },
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Balance(value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Saldo",
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
fun TransactionItem(item: Date, modifier: Modifier = Modifier) {
    val date = TRANSACTION_DATE_FORMAT.format(item)
    ListItem(
        modifier = modifier,
        leadingContent = {
            Icon(
                imageVector = Icons.Default.ArrowCircleDown,
                contentDescription = "Ícone de casa"
            )
        },
        headlineContent = { Text("Transação") },
        supportingContent = {
            Text("R$ 10.020,00", color = extendedColorScheme.success)
        },
        trailingContent = { Text(date) }
    )
}

@Preview(showBackground = true)
@Composable
fun BalancegPreview() {
    DesignSystemTheme {
        Balance("Android")
    }
}