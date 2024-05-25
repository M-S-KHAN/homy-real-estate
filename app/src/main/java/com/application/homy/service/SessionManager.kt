package com.application.homy.service

import android.content.Context
import androidx.compose.ui.semantics.Role
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("homy_prefs", Context.MODE_PRIVATE)

    fun saveUserInfo(token: String, role: String) {
        sharedPreferences.edit().putString("USER_ID", token).apply()
        sharedPreferences.edit().putString("ROLE", role).apply()
    }

    fun fetchUserId(): String? {
        return sharedPreferences.getString("USER_ID", null)
    }

    fun fetchUserRole(): String? {
        return sharedPreferences.getString("ROLE", null)
    }

    fun isLoggedIn(): Boolean {
        return fetchUserId() != null
    }

    fun logout() {
        sharedPreferences.edit().remove("USER_ID").apply()
    }
}
