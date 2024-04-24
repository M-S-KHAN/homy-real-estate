package com.application.homy.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("homy_prefs", Context.MODE_PRIVATE)

    fun saveUserId(token: String) {
        sharedPreferences.edit().putString("USER_ID", token).apply()
    }

    fun fetchUserId(): String? {
        return sharedPreferences.getString("USER_ID", null)
    }

    fun isLoggedIn(): Boolean {
        return fetchUserId() != null
    }

    fun logout() {
        sharedPreferences.edit().remove("USER_ID").apply()
    }
}
