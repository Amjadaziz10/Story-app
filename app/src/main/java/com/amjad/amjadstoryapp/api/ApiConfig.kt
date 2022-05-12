package com.amjad.amjadstoryapp.api

import com.amjad.amjadstoryapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}
