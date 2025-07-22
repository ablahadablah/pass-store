package com.pxl.pixelstore.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.pxl.pixelstore.domain.entity.PasswordRecord
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
sealed class Destination {
    @Serializable
    class PasswordsList : Destination()

    @Serializable
    data class PasswordDetails(
        val recordId: String?,
    ) : Destination()

    @Serializable
    data class EditPassword(val recordId: String? = null) : Destination()

    @Serializable
    object MasterPassword : Destination()
}

val PasswordRecordNavType = object : NavType<PasswordRecord>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PasswordRecord? {
        return PasswordRecord(
            bundle.getString("id")?.let { UUID.fromString(it) },
            url = bundle.getString("url")  ?: "",
            username = bundle.getString("username") ?: "",
            password = bundle.getString("password") ?: "",
            timeCreated = bundle.getLong("timeCreated", 0),
            timeLastUsed = bundle.getLong("timeLastUsed", 0),
            timePasswordChanged = bundle.getLong("timePasswordChanged", 0),
            deletedTime = bundle.getLong("deletedTime", 0),
            timeLastSynced = bundle.getLong("timeLastSynced", 0),
        )
    }

    override fun parseValue(value: String): PasswordRecord {
        return PasswordRecord()
    }

    override fun put(bundle: Bundle, key: String, value: PasswordRecord) {
        bundle.putString("id", value.id?.toString() ?: "")
        bundle.putString("url", value.url)
        bundle.putString("username", value.username)
        bundle.putString("password", value.password)
        bundle.putLong("timeCreated", value.timeCreated)
        bundle.putLong("timeLastUsed", value.timeLastUsed)
        bundle.putLong("timePasswordChanged", value.timePasswordChanged)
        bundle.putLong("deletedTime", value.deletedTime)
        bundle.putLong("timeLastSynced", value.timeLastSynced)
    }
}

