package com.jumia.myapplication.ui.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.infrastructure.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJumiaApi(@ApplicationContext context: Context): JumiaWebService {
        return RetrofitFactory(OkHttpClientProvider().provideOkHttpClient).apiService
    }

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideProductRepo(api: JumiaWebService) = ProductRepo(api)

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideConfigurationRepo(api: JumiaWebService) = ConfigurationRepo(api)
}