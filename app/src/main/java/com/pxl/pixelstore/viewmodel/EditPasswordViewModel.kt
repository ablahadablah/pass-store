package com.pxl.pixelstore.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.domain.usecase.SavePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EditPasswordState {
    data object Empty : EditPasswordState()
    data object SaveInProgress : EditPasswordState()
    data object Success : EditPasswordState()
    data class Error(val msg: String) : EditPasswordState()
}

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    private val savePasswordUseCase: SavePasswordUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<EditPasswordState>(EditPasswordState.Empty)
    val state: StateFlow<EditPasswordState>
        get() = _state

    var record: PasswordRecord? = null
    var urlText: MutableState<String> = mutableStateOf("")
    var loginText: MutableState<String> = mutableStateOf("")
    var passwordText: MutableState<String> = mutableStateOf("")

    val title: String
        get() = if (record == null) {
            "Add a new password"
        } else {
            "Edit passowrd"
        }

    fun init(record: PasswordRecord?) {
        this.record = record
        urlText.value = record?.url ?: ""
        loginText.value = record?.username ?: ""
        passwordText.value = record?.password ?: ""
    }

    fun saveEntity(url: String, login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = EditPasswordState.SaveInProgress

            try {
                val new = createEntity(record, url, login, password)
                savePasswordUseCase.savePassword(new)
                _state.value = EditPasswordState.Success
//                router.back()
            } catch (e: Throwable) {
                Log.e("qq", "Error saving a password record", e)
                _state.value = EditPasswordState.Error("An error saving password record: ${e.message}")
            }
        }
    }

    private fun createEntity(oldEntity: PasswordRecord?, url: String, login: String, password: String) =
        (oldEntity ?: PasswordRecord(id = UUID.randomUUID())).copy(
            url = url,
            username = login,
            password = password
        )
}