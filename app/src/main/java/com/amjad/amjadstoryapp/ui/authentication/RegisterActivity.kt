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
import com.amjad.amjadstoryapp.data.model.RegisterRequest
import com.amjad.amjadstoryapp.data.model.RegisterResponse
import com.amjad.amjadstoryapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        playAnimation()
        setMyButtonEnable()
        buttonChanger()
        setupAction()
    }

    private fun playAnimation(){
        val logo = ObjectAnimator.ofFloat(binding.titleTv, View.TRANSLATION_X, -1000f, 0f).setDuration(500)
        val logo2 = ObjectAnimator.ofFloat(binding.titleTv, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameEt, View.TRANSLATION_Y, 1000f, 0f).setDuration(500)
        val name2 = ObjectAnimator.ofFloat(binding.nameEt, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailEt, View.TRANSLATION_Y, 1000f, 0f).setDuration(500)
        val email2 = ObjectAnimator.ofFloat(binding.emailEt, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordEt, View.TRANSLATION_Y, 1000f, 0f).setDuration(500)
        val password2 = ObjectAnimator.ofFloat(binding.passwordEt, View.ALPHA, 1f).setDuration(500)
        val buttonRegister = ObjectAnimator.ofFloat(binding.registerButton, View.TRANSLATION_Y, 1000f, 0f).setDuration(500)
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
        val togetherName = AnimatorSet().apply {
            playTogether(name, name2)
        }
        val togetherBtnRegister = AnimatorSet().apply {
            playTogether(buttonRegister, buttonRegister2)
        }

        AnimatorSet().apply {
            playSequentially(together, togetherName, togetherEmail,togetherPassword, togetherBtnRegister )
            start()
        }
    }

    private fun setMyButtonEnable() {
        val name = binding.nameEt.text
        val email = binding.emailEt.text
        val password = binding.passwordEt.text

        binding.registerButton.isEnabled = name != null && name.toString().isNotEmpty() && email != null && email.toString().isNotEmpty() && password != null && password.toString().length > 5
    }

    private fun buttonChanger(){
        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

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

    private fun setupAction() {
        binding.registerButton.setOnClickListener{
            showLoading(true)
            register()
        }
    }

    private fun register() {
        val register = RegisterRequest()
        register.name = binding.nameEt.text.toString()
        register.email = binding.emailEt.text.toString()
        register.password = binding.passwordEt.text.toString()

        val client = ApiConfig.getApiService().postRegister(register)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showLoading(false)
                    Toast.makeText(this@RegisterActivity, "Registration Failed : ${response.body()?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}