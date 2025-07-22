package com.pxl.pixelstore.domain.encryption

import android.util.Log
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class PasswordCipher @Inject constructor(
    private val keysManager: KeysManager
) {
    companion object {
        private const val ALGORITHM = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH = 128
        private const val IV_LENGTH_BYTES = 12
    }

    fun encrypt(value: String): String {
        try {
            val password = value.toByteArray(Charsets.UTF_8)
            
            val iv = ByteArray(IV_LENGTH_BYTES)
            SecureRandom().nextBytes(iv)
            
            val cipher = Cipher.getInstance(ALGORITHM)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.ENCRYPT_MODE, keysManager.key, gcmSpec)
            
            val encryptedPassword = cipher.doFinal(password)
            
            val combined = ByteArray(iv.size + encryptedPassword.size)
            System.arraycopy(iv, 0, combined, 0, iv.size)
            System.arraycopy(encryptedPassword, 0, combined, iv.size, encryptedPassword.size)
            
            val encodedData = Base64.getEncoder().encodeToString(combined)

            return encodedData
        } catch (e: Exception) {
            // Log the error but don't expose details in production
            // In a real implementation, this should use a proper logging framework
            // and potentially report the error to a monitoring service
            throw SecurityException("Failed to encrypt a value: ${e.javaClass.simpleName}", e)
        }
    }
    
    fun decrypt(value: String): String {
        try {
            val combined = Base64.getDecoder().decode(value)
            
            if (combined.size < IV_LENGTH_BYTES) {
                throw SecurityException("Invalid encrypted data format")
            }
            
            val iv = ByteArray(IV_LENGTH_BYTES)
            val encryptedPassword = ByteArray(combined.size - IV_LENGTH_BYTES)
            
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH_BYTES)
            System.arraycopy(combined, IV_LENGTH_BYTES, encryptedPassword, 0, encryptedPassword.size)
            
            val cipher = Cipher.getInstance(ALGORITHM)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.DECRYPT_MODE, keysManager.key, gcmSpec)
            
            val decryptedBytes = cipher.doFinal(encryptedPassword)
            val decryptedPassword = String(decryptedBytes, Charsets.UTF_8)
            
            return decryptedPassword
        } catch (e: Exception) {
            Log.e("qq", "decrypt($value) exception", e)
            throw SecurityException("Failed to decrypt a value: ${e.javaClass.simpleName}", e)
        }
    }
}