package org.apiplayground.apitest

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass


internal val payloadModule: SerializersModule = SerializersModule {
    polymorphic(JsonElement::class) {
        subclass(JsonPrimitive::class)
//        subclass(JsonObject::class)
    }
}

internal val SharedPayloadJson: Json = Json {
    serializersModule = payloadModule
}

/**
 * A string serializer as an object, and workaround for
 * [kotlinx.serialization-1252](https://github.com/Kotlin/kotlinx.serialization/issues/1252).
 */
internal object StringAsObjectSerializer : KSerializer<String> {

    @JvmInline
    @Serializable
    @SerialName("String")
    value class StringSurrogate(val value: String)

    @Suppress("UNCHECKED_CAST")
    val serializer: KSerializer<StringAsObjectSerializer> =
        StringSurrogate.serializer() as KSerializer<StringAsObjectSerializer>

    override val descriptor: SerialDescriptor = StringSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: String) {
        StringSurrogate.serializer().serialize(encoder, StringSurrogate(value));
    }

    override fun deserialize(decoder: Decoder): String {
        return decoder.decodeSerializableValue(StringSurrogate.serializer()).value
    }
}
