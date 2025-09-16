package com.fjvid.taskbox.ui.notification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjvid.taskbox.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la gestion de la informacion de las preferencias del usuario.
 * EN este caso guarda solo si ha contestado definitivamente la solicitud de permisos o no
 */
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    var wasRequest = mutableStateOf(false)
        private set

    fun getNotificationPermission(){
        viewModelScope.launch {
            wasRequest.value = preferencesRepository.getNotificationPermission()
        }
    }

    fun saveNotificationPermission(granted: Boolean) {
        viewModelScope.launch {
            preferencesRepository.saveNotificationPermission(granted)
        }
    }

}