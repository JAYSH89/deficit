package data.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import models.ActivityFactor

object ActivityFactorSerializer : KSerializer<ActivityFactor> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "ActivityFactor",
        kind = PrimitiveKind.STRING,
    )

    override fun serialize(encoder: Encoder, value: ActivityFactor) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): ActivityFactor {
        return ActivityFactor.fromString(decoder.decodeString())
    }
}