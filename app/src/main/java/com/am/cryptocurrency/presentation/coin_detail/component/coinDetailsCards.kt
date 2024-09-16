package com.am.cryptocurrency.presentation.coin_detail.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.am.cryptocurrency.R
import com.am.cryptocurrency.data.remote.dto.TeamMember
import com.am.cryptocurrency.domain.model.CoinDetail
import com.am.cryptocurrency.presentation.ui.theme.CryptocurrencyTheme
import com.am.cryptocurrency.util.Constants.DESCRIPTION_TEXT_LINES


@Composable
fun CoinDetailsPage(
    coinDetail: CoinDetail,
    modifier: Modifier = Modifier
) {
    val gradiantAnim = remember { Animatable(0f) }
    val strokeProgressAnim = remember { Animatable(0f) }
    val smallPadding = dimensionResource(R.dimen.small_padding)
    val scrollState = rememberScrollState()
    val hBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.primary,
        ),
        startX = 0f,
        endX = gradiantAnim.value
    )
    val vBrush = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.primary,
        ),
        startY = 0f,
        endY = gradiantAnim.value
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(smallPadding),
        modifier = modifier.verticalScroll(scrollState)
    ) {
        CoinTitle(
            coinDetail.name,
            coinDetail.symbol,
            MaterialTheme.colorScheme.primary,
            smallPadding,
            strokeProgressAnim.value,
            Modifier.fillMaxWidth()
        )
        RankCard(
            coinDetail.rank,
            coinDetail.isActive,
            MaterialTheme.colorScheme.primary,
            smallPadding,
            strokeProgressAnim.value,
            Modifier.fillMaxWidth()
        )
        if (coinDetail.description.isNotBlank())
            DescriptionCard(
                coinDetail.description,
                vBrush,
                Modifier
                    .fillMaxWidth()
            )
        TagsRow(
            coinDetail.tags,
            hBrush,
            Modifier.fillMaxWidth()
        )
        if (coinDetail.team.isNotEmpty())
            TeamMemberCard(
                coinDetail.team,
                vBrush,
                Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )
    }
    LaunchedEffect(Unit){
        strokeProgressAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(5000, delayMillis = 1000)
        )
        gradiantAnim.animateTo(
            1000f,
            animationSpec = tween(durationMillis = 7000, delayMillis = 50)
        )
    }
}

@Composable
fun CoinTitle(
    name: String,
    symbol: String,
    color: Color,
    sharpnessDp: Dp,
    progress: Float,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = CutCornerShape(sharpnessDp),
        border = BorderStroke(1.dp, color),
        modifier = modifier
            .drawStokeOver(sharpnessDp, progress, MaterialTheme.colorScheme.tertiary)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.tiny_padding)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = symbol,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RankCard(
    rank: Int,
    active: Boolean,
    color: Color,
    sharpnessDp: Dp,
    progress: Float,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = CutCornerShape(sharpnessDp),
        border = BorderStroke(1.dp, color),
        modifier = modifier
            .drawEdgesOver(sharpnessDp, progress, MaterialTheme.colorScheme.tertiary)
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(text = "Ranked $rank")
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if(active)
                    stringResource(R.string.active)
                else
                    stringResource(R.string.not_active)
            )
        }
    }
}

@Composable
fun DescriptionCard(
    description: String,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    OutlinedCard(
        shape = CutCornerShape(dimensionResource(R.dimen.small_padding)),
        border = BorderStroke(1.dp, brush),
        modifier = modifier,
        onClick = { expanded = !expanded }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ){
            Text(
                text = description,
                maxLines = if (expanded)
                    Int.MAX_VALUE
                else
                    DESCRIPTION_TEXT_LINES,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            )
        }
    }
}

@Composable
fun TagsRow(
    tags: List<String>,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    val tagModifier = Modifier.padding(dimensionResource(R.dimen.tiny_padding))
    val colorfulTagModifier = Modifier
        .background(MaterialTheme.colorScheme.primary.copy(alpha = .3f))
        .padding(dimensionResource(R.dimen.tiny_padding))
    OutlinedCard(
        shape = RectangleShape,
        border = BorderStroke(1.dp, brush),
        modifier = modifier
    ) {
        LazyRow {
            itemsIndexed(tags){ index, tag ->
                if (index % 2 == 0)
                    Text(
                        text = tag.lowercase(),
                        modifier = tagModifier
                    )
                else
                    Text(
                        text = tag.lowercase(),
                        modifier = colorfulTagModifier
                    )
            }
        }
    }
}

@Composable
fun TeamMemberCard(
    teamMembers: List<TeamMember>,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    val memberModifier = Modifier
        .padding(dimensionResource(R.dimen.tiny_padding))
        .fillMaxWidth()
    OutlinedCard(
        shape = CutCornerShape(dimensionResource(R.dimen.small_padding)),
        border = BorderStroke(1.dp, brush),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.small_padding))
        ){
            LazyColumn {
                itemsIndexed(teamMembers, key = { _, member -> member.id }){ index, member ->
                    TeamMemberItem(member, memberModifier)
                    if (index != teamMembers.size-1)
                        HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun TeamMemberItem(
    member: TeamMember,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = member.name,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = member.position,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Preview
@Composable
private fun CoinDetailsScreenPreview() {
    CryptocurrencyTheme(darkTheme = true) {
        val coinDetail =CoinDetail(
            "id",
            "coin_name",
            "...".repeat(100),
            "@#$",
            10,
            false,
            listOf(
                "TAG_A",
                "TAG_B",
                "TAG_C",
                "TAG_D",
                "TAG_F",
                "TAG_G",
                "TAG_F",
                "TAG_TT",
            ),
            listOf(
                TeamMember("0", "AAAAA", "1"),
                TeamMember("1", "BBBBB", "10"),
                TeamMember("2", "CCCCC", "110"),
                TeamMember("3", "DDDDD", "152"),
            )
        )
        CoinDetailsPage(coinDetail, Modifier.padding(16.dp))
    }
}