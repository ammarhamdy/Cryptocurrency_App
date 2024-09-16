package com.am.cryptocurrency.di

import com.am.cryptocurrency.data.remote.CoinApi
import com.am.cryptocurrency.data.repository.CoinRepositoryImp
import com.am.cryptocurrency.domain.repository.CoinRepository
import com.am.cryptocurrency.util.Constants.BAS_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideCoinApi(): CoinApi =
        Retrofit
            .Builder()
            .baseUrl(BAS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)

    @Provides
    @Singleton
    fun provideCoinRepository(coinApi: CoinApi): CoinRepository =
        CoinRepositoryImp(coinApi)


}