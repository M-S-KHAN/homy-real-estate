package com.application.homy.presentation.viewmodel

import androidx.lifecycle.*
import com.application.homy.service.ApiService
import com.application.homy.service.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService, private val sessionManager: SessionManager
) : ViewModel() {

    fun logout() {
        sessionManager.logout()
    }
}
