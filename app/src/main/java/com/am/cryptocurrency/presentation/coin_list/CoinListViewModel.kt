package com.am.cryptocurrency.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.cryptocurrency.domain.use_case.GetCoinsUseCase
import com.am.cryptocurrency.util.Constants.TIME_TO_RE_EMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel(){

    private val _coinsState = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val coinsState: StateFlow<CoinsState> = _coinsState
        .flatMapLatest { getCoinsUseCase() }
        .stateIn(
            initialValue = CoinsState(isLoading = true),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_TO_RE_EMIT)
        )

    init {
        reload()
    }

    fun reload(){
        viewModelScope.launch {
            _coinsState.emit(Unit)
        }
    }

}