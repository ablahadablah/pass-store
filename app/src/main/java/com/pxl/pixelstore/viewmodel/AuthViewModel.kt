package com.pxl.pixelstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pxl.pixelstore.domain.usecase.InvalidMasterPasswordException
import com.pxl.pixelstore.domain.usecase.VerifyMasterPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthEvent {
    object Success : AuthEvent()
    sealed class Error(val msg: String?) : AuthEvent() {
        object InvalidMasterPassword : Error(null)
        class Unknown(msg: String? = null) : Error(msg)
    }
}

@HiltViewModel
class MasterPasswordViewModel @Inject constructor(
    private val verifyMasterPasswordUseCase: VerifyMasterPasswordUseCase
) : ViewModel() {
    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    fun verifyMasterPassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val valid = verifyMasterPasswordUseCase.verifyMasterPassword(password)
                if (valid) {
                    _events.emit(AuthEvent.Success)
                } else {
                    _events.emit(AuthEvent.Error.InvalidMasterPassword)
                }
            } catch (e: InvalidMasterPasswordException) {
                _events.emit(AuthEvent.Error.InvalidMasterPassword)
            } catch (e: SecurityException) {
                _events.emit(AuthEvent.Error.InvalidMasterPassword)
            } catch (e: Exception) {
                _events.emit(AuthEvent.Error.Unknown())
            }
        }
    }
}
