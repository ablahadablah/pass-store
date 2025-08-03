package com.pxl.pixelstore.viewmodel.password

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.entity.PasswordRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EditPasswordUiEvent {
    data object PasswordSaved : EditPasswordUiEvent()
    data class Error(val msg: String) : EditPasswordUiEvent()
}

sealed class EditPasswordUiState {
    data object Loading : EditPasswordUiState()
    data object SaveInProgress : EditPasswordUiState()
    data class Data(val record: PasswordRecord?) : EditPasswordUiState()
}

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    private val passwordRecordRepository: PasswordRecordRepository
) : ViewModel() {
    private val _events = Channel<EditPasswordUiEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow<EditPasswordUiState>(EditPasswordUiState.Loading)
    val state: StateFlow<EditPasswordUiState>
        get() = _state

    fun loadRecord(recordId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (recordId == null) {
                _state.emit(EditPasswordUiState.Data(null))
            } else {
                _state.emit(EditPasswordUiState.Loading)

                val record = passwordRecordRepository.findById(recordId)

                if (record == null) {
                    _events.send(EditPasswordUiEvent.Error("Record not found"))
                } else {
                    _state.emit(EditPasswordUiState.Data(record))
                }
            }
        }
    }

    fun saveEntity(url: String, login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value
            val record = if (state is EditPasswordUiState.Data) {
                state.record
            } else {
                null
            }

            _state.value = EditPasswordUiState.SaveInProgress

            try {
                val new = createEntity(record, url, login, password)
                passwordRecordRepository.savePassword(new)
                // send one-time event
                _events.send(EditPasswordUiEvent.PasswordSaved)
            } catch (e: Throwable) {
                Log.e("qq", "Error saving a password record", e)
                _events.send(EditPasswordUiEvent.Error("An error saving password record: ${e.message}"))
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