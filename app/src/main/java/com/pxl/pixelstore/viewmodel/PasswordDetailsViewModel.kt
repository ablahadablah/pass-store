package com.pxl.pixelstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.domain.usecase.DeletePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Loading : UiState()
    object Error : UiState()
    data class Data(val record: PasswordRecord) : UiState()
}

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val passwordRecordRepository: PasswordRecordRepository,
    private val deletePasswordUseCase: DeletePasswordUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state = _state.asStateFlow()

    fun loadPasswordRecord(id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(UiState.Loading)

            if (id == null) {
                Log.d("qq", "password record id is null, can't do anything")
                return@launch // error here
            }

            val record = passwordRecordRepository.findById(id)

            if (record == null) {
                _state.emit(UiState.Error)
            } else {
                _state.emit(UiState.Data(record))
            }
        }
    }

    fun deleteEntity(record: PasswordRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePasswordUseCase.deletePassword(record)
        }
    }
}