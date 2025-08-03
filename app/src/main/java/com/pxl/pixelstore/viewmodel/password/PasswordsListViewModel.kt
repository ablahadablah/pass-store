package com.pxl.pixelstore.viewmodel.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.domain.usecase.ImportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PasswordsScreenState {
    object Empty : PasswordsScreenState()

    object Loading : PasswordsScreenState()

    data class Data(
        val passwords: List<PasswordRecord>,
        val selectedEntity: PasswordRecord?
    ) : PasswordsScreenState()

    // state or event?
    data class Error(val msg: String) : PasswordsScreenState()
}

@HiltViewModel
class PasswordsListViewModel @Inject constructor(
    private val importUseCase: ImportUseCase,
    private val passwordRecordRepository: PasswordRecordRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<PasswordsScreenState>(PasswordsScreenState.Empty)
    val state: StateFlow<PasswordsScreenState> = _state
        .onStart {
            delay(500)
            init()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PasswordsScreenState.Loading
        )

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: catch errors?
            _state.value = PasswordsScreenState.Loading

            passwordRecordRepository.passwords().collect { passwords ->
                if (passwords.isEmpty()) {
                    _state.value = PasswordsScreenState.Empty
                } else {
                    _state.value = PasswordsScreenState.Data(
                        passwords,
                        null
                    )
                }
            }
        }
    }

    fun searchPasswords(string: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (string.isEmpty()) {
                init()
            } else {
                val passwords = passwordRecordRepository.findByString(string)
//                val passwords = loadPasswordsUseCase.searchPasswords(string)
                _state.value = PasswordsScreenState.Data(
                    passwords,
                    null
                )
            }
        }
    }

    fun startImport(file: String) {
        println("startImport: $file")
        viewModelScope.launch(Dispatchers.IO) {
            importUseCase.importFromFile(file)

            init()
        }
    }
}