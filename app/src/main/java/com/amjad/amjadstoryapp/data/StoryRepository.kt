package com.amjad.amjadstoryapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.amjad.amjadstoryapp.api.ApiService
import com.amjad.amjadstoryapp.data.database.StoryDatabase
import com.amjad.amjadstoryapp.data.model.Story

class StoryRepository(private val apiService: ApiService, private val storyDatabase: StoryDatabase) {
    @OptIn(ExperimentalPagingApi::class)
    fun getAllStories(token: String): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
//            pagingSourceFactory = { StoryPagingSource(apiService, "Bearer $token") }
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),

            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}