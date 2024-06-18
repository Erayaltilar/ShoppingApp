package com.example.eray_altilar_final.core

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREFS_NAME = "AppPreferences"
    private const val TOKEN_KEY = "auth_token"
    private const val USER_ID = "user_id"
    private lateinit var sharedPreferences: SharedPreferences

    // initialize function to be called once in Application class or MainActivity
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(userId: Long) {
     val editor = sharedPreferences.edit()
     editor.putLong(USER_ID, userId)
     editor.apply()
    }

    fun getUserId(): Long {
        return sharedPreferences.getLong(USER_ID, 0)
    }

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }
}