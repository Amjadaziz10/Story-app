package com.amjad.amjadstoryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class LoginResponse(
    val message: String? = null,
    val loginResult: User? = null
)

@Parcelize
data class User(
    val userId: String,
    val name: String,
    val token: String
): Parcelable