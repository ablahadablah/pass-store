package com.pxl.pixelstore.domain.encryption

import android.content.Context
import android.util.Base64
import android.util.Base64.encodeToString
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeysManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private const val KEY_ITERATIONS = 65536
        private const val KEY_LENGTH = 256
    }

    var key: SecretKeySpec? = null
        private set

    fun generateKey(password: String, userSalt: ByteArray): String {
        val keyBytes = deriveKeyBytesFromPassword(password, userSalt)
        key = SecretKeySpec(keyBytes, "AES")
        val keyToStore = encodeToString(keyBytes, Base64.DEFAULT)
        return keyToStore
    }

    fun generateRandomSalt(): ByteArray {
        return ByteArray(16).apply { SecureRandom().nextBytes(this) }
    }

    private fun deriveKeyBytesFromPassword(password: String, salt: ByteArray): ByteArray {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(password.toCharArray(), salt, KEY_ITERATIONS, KEY_LENGTH)
        return factory.generateSecret(spec).encoded
    }
}