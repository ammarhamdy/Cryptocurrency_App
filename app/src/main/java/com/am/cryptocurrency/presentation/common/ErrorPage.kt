package com.am.cryptocurrency.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.am.cryptocurrency.R

@Composable
fun ErrorPage(
    errorType: ErrorType,
    modifier: Modifier = Modifier,
    reload: (() -> Unit)? = null
) {
    reload?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Text(
                text = if (errorType == ErrorType.CONNECTION_ERROR)
                    stringResource(R.string.connection_error)
                else
                    stringResource(R.string.unexpected_error),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_padding)))
            TextButton(onClick = reload) {
                Text(text = stringResource(R.string.reload))
            }
        }
    } ?:
    Text(
        text = if (errorType == ErrorType.CONNECTION_ERROR)
            stringResource(R.string.connection_error)
        else
            stringResource(R.string.unexpected_error),
        style = MaterialTheme.typography.headlineSmall
    )

}