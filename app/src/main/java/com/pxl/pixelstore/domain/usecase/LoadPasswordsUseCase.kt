package com.pxl.pixelstore.domain.usecase

import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.encryption.PasswordCipher
import com.pxl.pixelstore.domain.entity.PasswordRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadPasswordsUseCase @Inject constructor(
    private val passwordRecordRepository: PasswordRecordRepository,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val passwordCipher: PasswordCipher
) {
    val passwords: Flow<List<PasswordRecord>>
        get() = passwordRecordRepository.passwords().map { data ->
            data.map {
                it.copy(
                    password = passwordCipher.decrypt(it.password)
                )
            }
        }

    suspend fun searchPasswords(string: String): List<PasswordRecord> {
        return passwordRecordRepository.findByString(string)
            .map { r -> r.copy(password = passwordCipher.decrypt(r.password)) }
    }
}