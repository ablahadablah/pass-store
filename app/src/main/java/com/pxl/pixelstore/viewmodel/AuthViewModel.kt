package com.pxl.pixelstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pxl.pixelstore.domain.usecase.InvalidMasterPasswordException
import com.pxl.pixelstore.domain.usecase.VerifyMasterPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthEvent {
    object LoginSuccess : AuthEvent()
    sealed class Error(val msg: String?) : AuthEvent() {
        object InvalidMasterPassword : Error(null)
        class Unknown(msg: String? = null) : Error(msg)
    }
}

@HiltViewModel
class MasterPasswordViewModel @Inject constructor(
    private val verifyMasterPasswordUseCase: VerifyMasterPasswordUseCase
) : ViewModel() {
    private val _events = Channel<AuthEvent>()
    val events = _events.receiveAsFlow()

    fun verifyMasterPassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valid = verifyMasterPasswordUseCase.verifyMasterPassword(password)
                if (valid) {
                    _events.send(AuthEvent.LoginSuccess)
                } else {
                    _events.send(AuthEvent.Error.InvalidMasterPassword)
                }
            } catch (e: InvalidMasterPasswordException) {
                _events.send(AuthEvent.Error.InvalidMasterPassword)
            } catch (e: SecurityException) {
                _events.send(AuthEvent.Error.InvalidMasterPassword)
            } catch (e: Exception) {
                _events.send(AuthEvent.Error.Unknown())
            }
        }
    }
}
