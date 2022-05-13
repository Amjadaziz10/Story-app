package com.amjad.amjadstoryapp.ui.story

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.amjadstoryapp.R
import com.amjad.amjadstoryapp.animation.startAnimation
import com.amjad.amjadstoryapp.databinding.ActivityStoryBinding
import com.amjad.amjadstoryapp.helper.PreferenceHelper
import com.amjad.amjadstoryapp.ui.ViewModelFactory
import com.amjad.amjadstoryapp.ui.adapter.ListStoryAdapter
import com.amjad.amjadstoryapp.ui.adapter.LoadingStateAdapter
import com.amjad.amjadstoryapp.ui.authentication.MainActivity
import com.amjad.amjadstoryapp.ui.story.addstory.AddStoryActivity
import com.amjad.amjadstoryapp.ui.story.storymap.MapsActivity


class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var sharedPreference: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreference = PreferenceHelper(this)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_explosion_animation).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        title = "${getString(R.string.welcome)}, ${sharedPreference.getString(PreferenceHelper.PREF_NAME)} !"

        showLoading(true)
        getStory()

        binding.rvStory.layoutManager = LinearLayoutManager(this)

        binding.fabAdd.setOnClickListener {
            binding.fabAdd.visibility = View.INVISIBLE
            binding.circleView.isVisible = true
            binding.circleView.startAnimation(animation){
                binding.blueScreen.visibility = View.VISIBLE
                val intent = Intent(this, AddStoryActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        getStory()
        binding.fabAdd.visibility = View.VISIBLE
        binding.blueScreen.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_menu -> {
                sharedPreference.clear()
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
                true
            }

            R.id.language_menu -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            R.id.map_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun getStory(){
        val adapter = ListStoryAdapter()
        val token = sharedPreference.getString(PreferenceHelper.PREF_TOKEN)

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        if (token != null) {
            viewModel.setStories(token).observe(this) {
                adapter.submitData(lifecycle, it)
                showLoading(false)
            }
        }
    }


    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}