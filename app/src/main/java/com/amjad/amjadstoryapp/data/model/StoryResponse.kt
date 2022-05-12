package com.amjad.amjadstoryapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<Story>
)

@Parcelize
@Entity(tableName = "story")
data class Story(
    @PrimaryKey
    val id: String,
    val name: String?,
    val description: String?,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: String?,

    val lat: Double,
    val lon: Double
): Parcelable