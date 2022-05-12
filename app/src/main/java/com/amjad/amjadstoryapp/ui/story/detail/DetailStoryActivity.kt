package com.amjad.amjadstoryapp.ui.story.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amjad.amjadstoryapp.data.model.Story
import com.amjad.amjadstoryapp.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        val story = intent.getParcelableExtra<Story>("Story") as Story
        Glide.with(applicationContext)
            .load(story.photoUrl)
            .into(binding.storyImg)
        binding.nameTv.text = story.name
        binding.descTv.text = story.description
    }
}