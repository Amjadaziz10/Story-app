package com.amjad.amjadstoryapp.helper

import android.content.Context
import com.amjad.amjadstoryapp.api.ApiConfig
import com.amjad.amjadstoryapp.data.StoryRepository
import com.amjad.amjadstoryapp.data.database.StoryDatabase

class Injection {
    companion object {
        fun provideStoryRepository(context: Context): StoryRepository {
            val database = StoryDatabase.getDatabase(context)
            val apiService = ApiConfig.getApiService()
            return StoryRepository(apiService, database)
        }
    }
}