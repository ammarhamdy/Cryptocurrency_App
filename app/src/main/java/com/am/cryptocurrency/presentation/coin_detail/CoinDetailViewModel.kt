package com.am.cryptocurrency.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.cryptocurrency.domain.use_case.GetCoinUseCase
import com.am.cryptocurrency.util.Constants.COIN_ID_PARAM
import com.am.cryptocurrency.util.Constants.TIME_TO_RE_EMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    coinDetailUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var coinDetailState: StateFlow<CoinDetailState>? =
        savedStateHandle.get<String>(COIN_ID_PARAM)?.let{ id ->
            coinDetailUseCase(id)
                .stateIn(
                    initialValue = CoinDetailState(isLoading = true),
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIME_TO_RE_EMIT)
                )
        }

}
