package com.am.cryptocurrency.presentation.common

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.am.cryptocurrency.R
import com.am.cryptocurrency.presentation.ui.theme.CryptocurrencyTheme

private object Converter{
    val BooleanTwoWayConverter = TwoWayConverter<Boolean, AnimationVector1D>(
        convertToVector = { bool -> AnimationVector1D(if (bool) 1f else 0f) },
        convertFromVector = { vector -> vector.value == 1f }
    )
}

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
            TogglingText(
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
    TogglingText(
        text = if (errorType == ErrorType.CONNECTION_ERROR)
            stringResource(R.string.connection_error)
        else
            stringResource(R.string.unexpected_error),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun TogglingText(
    text: String,
    style: TextStyle
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition")
    val display by infiniteTransition.animateValue(
        initialValue = true,
        targetValue = false,
        typeConverter = Converter.BooleanTwoWayConverter,
        animationSpec = infiniteRepeatable(
            tween(2000, delayMillis = 2000),
            repeatMode = RepeatMode.Restart
        ),
        label = "display_animation"
    )
    Box(modifier = Modifier.wrapContentSize()){
        if (display)
            Text(
                coloredTextBaseCondition(
                    text,
                    MaterialTheme.colorScheme.tertiary
                ) { n -> n % 2 == 0 },
                style = style
            )
        else
            Text(
                coloredTextBaseCondition(
                    text,
                    MaterialTheme.colorScheme.tertiary
                ) { it % 2 != 0 },
                style = style
            )
    }
}

fun coloredTextBaseCondition(
    text: String,
    color: Color,
    condition: (Int)-> Boolean
) = buildAnnotatedString {
    val length = text.length
    var index = 0
    text.forEachIndexed{ i, c ->
        index += (i + (if (c==' ') 1 else 0)) % length // skip space character.
        if (condition(index)) withStyle(style = SpanStyle(color)) { append(c) }
        else append(c)
        index -= i // get back normal index sequence.
    }
}

@Preview
@Composable
private fun ErrorPagePreview() {
    CryptocurrencyTheme {
        ErrorPage(ErrorType.CONNECTION_ERROR)
    }
}