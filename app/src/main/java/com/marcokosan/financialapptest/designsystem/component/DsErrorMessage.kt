package com.marcokosan.financialapptest.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marcokosan.financialapptest.R

@Composable
fun DsErrorMessage(
    message: String?,
    modifier: Modifier = Modifier,
    onTryAgain: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message ?: stringResource(R.string.error_message))
        onTryAgain?.let {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onTryAgain) {
                Text(stringResource(R.string.try_again))
            }
        }
    }
}