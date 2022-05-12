package com.amjad.amjadstoryapp.ui.story.storymap

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amjad.amjadstoryapp.api.ApiConfig
import com.amjad.amjadstoryapp.data.model.Story
import com.amjad.amjadstoryapp.data.model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel: ViewModel() {
    val listStories = MutableLiveData<ArrayList<Story>>()

    fun setStories(token: String?){
        val client = ApiConfig.getApiService().getAllStoriesLoc("Bearer $token", 1,20,1)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if(response.isSuccessful){
                    listStories.postValue(response.body()?.listStory as ArrayList<Story>)
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }
        })

    }

    fun getStories(): LiveData<ArrayList<Story>> {
        return listStories
    }

}