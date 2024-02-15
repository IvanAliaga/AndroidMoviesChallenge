package com.app.bancamovil.di

import android.content.Context
import com.android.movieschallenge.App
import com.android.movieschallenge.data.network.MovieDataApi
import com.android.movieschallenge.di.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideMovieApiClient(retrofit: Retrofit): MovieDataApi{
        return retrofit.create(MovieDataApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private const val READ_TIMEOUT = 30
    private const val WRITE_TIMEOUT = 30
    private const val CONNECTION_TIMEOUT = 10
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGQyZGZhOTk3MTFkOTk1MmI0N2Y2NzlhOTExNGRlYiIsInN1YiI6IjY1Y2QzZWQxNmMwYjM2MDE3YjhkNDM4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.OYUcgyfJ6wG5VObXOQ745mxBZhsDv9P4Fqd9ESIdvsk"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)


        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            requestBuilder.addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $TOKEN")
            it.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

}