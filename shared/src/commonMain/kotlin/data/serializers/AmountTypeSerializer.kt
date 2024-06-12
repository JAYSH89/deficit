package data.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import models.AmountType

object AmountTypeSerializer: KSerializer<AmountType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "AmountType",
        kind = PrimitiveKind.STRING,
    )

    override fun serialize(encoder: Encoder, value: AmountType) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): AmountType {
        return AmountType.fromString(decoder.decodeString())
    }
}