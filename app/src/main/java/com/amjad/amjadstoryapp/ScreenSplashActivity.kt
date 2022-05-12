package com.amjad.amjadstoryapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.amjad.amjadstoryapp.databinding.ActivityScreenSplashBinding
import com.amjad.amjadstoryapp.ui.authentication.MainActivity

@SuppressLint("CustomSplashScreen")
class ScreenSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.scLogo.alpha = 0f
        binding.scLogo.animate().setDuration(1500).alpha(1f).withEndAction{
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}