package com.pxl.pixelstore.domain.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

@Serializable
data class PasswordRecord(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID? = null,
    val url: String = "",
    val username: String = "",
    val password: String = "",
    val timeCreated: Long = 0,
    val timeLastUsed: Long = 0,
    val timePasswordChanged: Long = 0,
    val deletedTime: Long = 0,
    val timeLastSynced: Long = 0
)