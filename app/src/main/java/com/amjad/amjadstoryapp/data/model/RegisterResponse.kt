package com.amjad.amjadstoryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterResponse (
    val message: String
): Parcelable