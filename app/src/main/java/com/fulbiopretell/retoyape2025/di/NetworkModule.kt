package com.fulbiopretell.retoyape2025.di

import com.fulbiopretell.retoyape2025.data.ApiService
import com.fulbiopretell.retoyape2025.data.IRepositoryApp
import com.fulbiopretell.retoyape2025.data.RepositoryApp
import com.fulbiopretell.retoyape2025.data.network.NetConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetConstants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepositoryApp(api: ApiService): IRepositoryApp {
        return RepositoryApp(api)
    }
}