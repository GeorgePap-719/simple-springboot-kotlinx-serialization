@file:OptIn(ExperimentalSerializationApi::class)

package org.apiplayground.apitest

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


public class AnySerializer : KSerializer<Any?> {
    private val delegateSerializer = MapSerializer(String.serializer(), String.serializer().nullable)
    override val descriptor: SerialDescriptor = SerialDescriptor("data", delegateSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: Any?) {
        val input = value as? String
        val data = hashMapOf(Pair("data", input))
        encoder.encodeSerializableValue(delegateSerializer, data)
    }

    override fun deserialize(decoder: Decoder): Any? {
        val value: Map<String, String?> = decoder.decodeSerializableValue(delegateSerializer)
        value.values.first()?.let {
            return value
        } ?: return null
    }
}