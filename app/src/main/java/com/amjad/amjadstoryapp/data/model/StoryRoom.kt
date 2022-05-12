package com.amjad.amjadstoryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class StoryRoom(
    @PrimaryKey
    val id: String?,
    val name: String?,
    val description: String?,
    val photoUrl: String?,
    val createdAt: String?,
    val lat: Double,
    val lon: Double
)