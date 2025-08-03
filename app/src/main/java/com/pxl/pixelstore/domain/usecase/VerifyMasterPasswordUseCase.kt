package com.pxl.pixelstore.domain.usecase

import android.util.Base64
import com.pxl.pixelstore.data.storage.AppDataStore
import com.pxl.pixelstore.domain.encryption.KeysManager
import com.pxl.pixelstore.domain.encryption.PasswordCipher
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class InvalidMasterPasswordException(msg: String?) : Exception(msg)

class VerifyMasterPasswordUseCase @Inject constructor(
    private val keyManager: KeysManager,
    private val cipher: PasswordCipher,
    private val dataStore: AppDataStore
) {
    companion object {
        const val VALIDATION_STRING_RAW = "VALID"
    }

    suspend fun verifyMasterPassword(password: String): Boolean {
        val salt = dataStore.getSalt().firstOrNull()

        if (salt.isNullOrBlank()) {
            // first app launch
            val randomSalt = keyManager.generateRandomSalt()

            keyManager.generateKey(password, randomSalt)
            val validationEncrypted = cipher.encrypt(VALIDATION_STRING_RAW)
            dataStore.putSalt(Base64.encodeToString(randomSalt, Base64.DEFAULT))
            dataStore.putValidationString(validationEncrypted)

            return true
        } else {
            val validationEncrypted = dataStore.getValidationString().firstOrNull()

            if (validationEncrypted.isNullOrBlank()) {
                throw InvalidMasterPasswordException("Validation string is empty, but the salt is here")
            }

            keyManager.generateKey(password, Base64.decode(salt, Base64.DEFAULT))

            val validationString = cipher.decrypt(validationEncrypted)
            // if the strings match, the key is correct
            return validationString == VALIDATION_STRING_RAW
        }
    }
}