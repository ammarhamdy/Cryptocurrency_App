package com.am.cryptocurrency.domain.use_case

import com.am.cryptocurrency.data.remote.dto.toCoinDetail
import com.am.cryptocurrency.domain.repository.CoinRepository
import com.am.cryptocurrency.presentation.coin_detail.CoinDetailState
import com.am.cryptocurrency.presentation.common.ErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

    operator fun invoke(coinId: String): Flow<CoinDetailState> = flow {
        try {
            emit(CoinDetailState(isLoading = true))
            emit(CoinDetailState(coinDetail = coinRepository.getCoin(coinId).toCoinDetail()))
        } catch (e: HttpException){
            emit(CoinDetailState(error = ErrorType.UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(CoinDetailState(error = ErrorType.UNEXPECTED_ERROR))
        }
    }

}