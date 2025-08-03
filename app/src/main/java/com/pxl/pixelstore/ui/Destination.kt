package com.pxl.pixelstore.ui

import kotlinx.serialization.Serializable

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