package com.application.homy.service

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker =
        NetworkChecker(context)

    @Singleton
    @Provides
    fun provideSnackbarManager(): SnackbarManager = SnackbarManager(CoroutineScope(Dispatchers.Main))

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
}
