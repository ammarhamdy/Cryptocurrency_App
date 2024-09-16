package com.am.cryptocurrency.presentation.common

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.am.cryptocurrency.presentation.ui.theme.CryptocurrencyTheme
import okhttp3.internal.toImmutableList


private object AnimationPro {
    val StringInterpolator = TwoWayConverter<Int, AnimationVector1D>(
        convertFromVector = { vector -> vector.value.toInt() },
        convertToVector = { intValue -> AnimationVector1D(intValue.toFloat()) }
    )
    val getFaster = Easing { fraction -> fraction * fraction }
}


@Composable
fun DancingText(
    text: String,
    modifier: Modifier = Modifier
) {
    val dancingTexts = remember { getDancingTexts(text) }
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition")
    val index by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = text.length + 1,
        typeConverter = AnimationPro.StringInterpolator,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (text.length / 2) * 900,
                delayMillis = 100,
                easing = AnimationPro.getFaster
        )
        ),
        label = "index animation"
    )
    Text(
        text = dancingTexts[index],
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
    )
}

private fun getDancingTexts(text: String) =
    List(text.length + 1){ index ->
        if (index == text.length) text
        else text.substring(0, index) +
                (if (text[index] == '.') ' ' else text[index].uppercase()) +
                text.substring(index+1)
    }.toImmutableList()


@Preview
@Composable
private fun DancingTextPreview() {
    CryptocurrencyTheme {
        DancingText(text = "loading...")
    }
}

