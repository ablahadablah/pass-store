package com.pxl.pixelstore.domain.usecase

import android.util.Log
import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.encryption.PasswordCipher
import com.pxl.pixelstore.domain.entity.PasswordRecord
import java.util.Calendar
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
    private val passwordRecordRepository: PasswordRecordRepository,
    private val passwordCipher: PasswordCipher
) {
    suspend fun savePassword(e: PasswordRecord) {
        val dt = Calendar.getInstance().timeInMillis
        val passwordRecord = e.copy(
            timeCreated = if (e.timeCreated == 0L) dt else e.timeCreated,
            timePasswordChanged = if (e.timePasswordChanged == 0L) dt else e.timePasswordChanged,
            timeLastUsed = if (e.timeLastUsed == 0L) dt else e.timeLastUsed
        )
        val encrypted = passwordCipher.encrypt(passwordRecord.password)

        passwordRecordRepository.savePassword(passwordRecord.copy(password = encrypted))

//        if (sessionManager.onlineMode) {
//            try {
//                passwordService.uploadPasswords(
//                    listOf(
//                        PasswordApiEntity(
//                            id = e.id,
//                            url = e.url,
//                            username = e.username,
//                            password = e.password,
//                            timeCreated = e.timeCreated,
//                            timePasswordChanged = e.timePasswordChanged,
//                            timeLastUsed = e.timeLastUsed
//                        )
//                    ),
//                    sessionManager.getAuthToken()
//                )
//            } catch (e: ConnectException) {
//                sessionManager.onlineMode = false
//            }
//        } else {
//            println("offline mode, cant upload password")
//        }
    }
}