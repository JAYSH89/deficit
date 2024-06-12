package data.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import models.Gender

object GenderSerializer : KSerializer<Gender> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Gender",
        kind = PrimitiveKind.STRING,
    )

    override fun serialize(encoder: Encoder, value: Gender) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Gender {
        return Gender.fromString(decoder.decodeString())
    }
}