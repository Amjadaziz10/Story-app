package com.amjad.amjadstoryapp.helper

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val PREF_NAME = "loginPref"
    private val sharedPreference : SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor : SharedPreferences.Editor = sharedPreference.edit()

    fun put(key: String, value: String){
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Boolean){
        editor.putBoolean(key,value)
            .apply()
    }

    fun getString(key: String) : String? {
        return sharedPreference.getString(key, null)
    }

    fun getBoolean(key: String): Boolean{
        return  sharedPreference.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }

    companion object{
        const val PREF_ISLOGIN = "is_login"
        const val PREF_USERID = "pref_userid"
        const val PREF_NAME = "pref_name"
        const val PREF_TOKEN = "pref_token"
    }
}