package com.amjad.amjadstoryapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amjad.amjadstoryapp.data.StoryRepository
import com.amjad.amjadstoryapp.data.model.Story

class StoryViewModel(private val storyRepo: StoryRepository): ViewModel() {

    fun setStories(token: String): LiveData<PagingData<Story>> {
        return storyRepo.getAllStories(token).cachedIn(viewModelScope)
    }

}