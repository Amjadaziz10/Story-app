package com.amjad.amjadstoryapp.api

import com.amjad.amjadstoryapp.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun postLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun postRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization")
        auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): StoryResponse

    @GET("stories")
    fun getAllStoriesLoc(
        @Header("Authorization")
        auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int = 0
    ): Call<StoryResponse>

    @POST("stories")
    @Multipart
    fun postStories(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Call<FileUploadResponse>

}