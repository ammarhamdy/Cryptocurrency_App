package com.am.cryptocurrency.domain.model

import androidx.compose.runtime.Immutable
import com.am.cryptocurrency.data.remote.dto.TeamMember

@Immutable
data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMember>
)