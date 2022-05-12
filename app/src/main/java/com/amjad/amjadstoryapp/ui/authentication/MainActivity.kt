package com.amjad.amjadstoryapp.ui.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amjad.amjadstoryapp.api.ApiConfig
import com.amjad.amjadstoryapp.data.model.LoginRequest
import com.amjad.amjadstoryapp.data.model.LoginResponse
import com.amjad.amjadstoryapp.databinding.ActivityMainBinding
import com.amjad.amjadstoryapp.helper.PreferenceHelper
import com.amjad.amjadstoryapp.ui.story.StoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        sharedPreference = PreferenceHelper(this)

        playAnimation()
        setupAction()
        setMyButtonEnable()
        buttonChanger()
    }

    private fun playAnimation(){
        val logo = ObjectAnimator.ofFloat(binding.imageView, View.SCALE_X, 0f, 1f).setDuration(1000)
        val logo2 = ObjectAnimator.ofFloat(binding.imageView, View.SCALE_Y, 0f, 1f).setDuration(1000)
        val email = ObjectAnimator.ofFloat(binding.emailEt, View.TRANSLATION_X, -100f, 0f).setDuration(500)
        val email2 = ObjectAnimator.ofFloat(binding.emailEt, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordEt, View.TRANSLATION_X, -100f, 0f).setDuration(500)
        val password2 = ObjectAnimator.ofFloat(binding.passwordEt, View.ALPHA, 1f).setDuration(500)
        val buttonLogin = ObjectAnimator.ofFloat(binding.loginButton, View.TRANSLATION_X, -100f, 0f).setDuration(500)
        val buttonLogin2 = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val buttonRegister = ObjectAnimator.ofFloat(binding.registerButton, View.TRANSLATION_X, -100f, 0f).setDuration(500)
        val buttonRegister2 = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(logo, logo2)
        }
        val togetherEmail = AnimatorSet().apply {
            playTogether(email, email2)
        }
        val togetherPassword = AnimatorSet().apply {
            playTogether(password, password2)
        }
        val togetherBtnLogin = AnimatorSet().apply {
            playTogether(buttonLogin, buttonLogin2)
        }
        val togetherBtnRegister = AnimatorSet().apply {
            playTogether(buttonRegister, buttonRegister2)
        }

        AnimatorSet().apply {
            playSequentially(together, togetherEmail,togetherPassword, togetherBtnLogin, togetherBtnRegister )
            start()
        }
    }


    private fun setMyButtonEnable() {
        val email = binding.emailEt.text
        val password = binding.passwordEt.text

        binding.loginButton.isEnabled = email != null && email.toString().isNotEmpty() && password != null && password.toString().length > 5
    }

    private fun buttonChanger(){
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if(sharedPreference.getBoolean(PreferenceHelper.PREF_ISLOGIN)){
            moveIntent()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            showLoading(true)
            login()
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val login = LoginRequest()
        login.email = binding.emailEt.text.toString()
        login.password = binding.passwordEt.text.toString()
        loginUser(login)

    }

    private fun loginUser(login: LoginRequest){
        val client = ApiConfig.getApiService().postLogin(login)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ){
                if(response.isSuccessful){
                    response.body()?.loginResult?.let { sharedPreference.put(PreferenceHelper.PREF_USERID, it.userId) }
                    response.body()?.loginResult?.let { sharedPreference.put(PreferenceHelper.PREF_NAME, it.name) }
                    response.body()?.loginResult?.let { sharedPreference.put(PreferenceHelper.PREF_TOKEN, it.token) }
                    response.body()?.loginResult?.let { sharedPreference.put(PreferenceHelper.PREF_ISLOGIN, true) }
                    showLoading(false)
                    Toast.makeText(this@MainActivity, "Welcome " + response.body()?.loginResult?.name, Toast.LENGTH_SHORT).show()
                    moveIntent()
                } else{
                    showLoading(false)
                    Toast.makeText(this@MainActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }

    private fun moveIntent(){
        startActivity(Intent(this, StoryActivity::class.java))
        finish()
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}