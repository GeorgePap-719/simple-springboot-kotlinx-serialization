package org.apiplayground.apitest

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement

public fun SharedPayload.encodeToJson(): JsonElement = SharedPayloadJson.encodeToJsonElement(
    SharedSharedPayloadFromBuilder.serializer(), this as SharedSharedPayloadFromBuilder
)

public fun JsonElement.decodeToSharedPayload(): SharedPayload = SharedPayloadJson.decodeFromJsonElement(
    SharedSharedPayloadFromBuilder.serializer(), this
)

public fun JsonObject.decodeToSharedPayload(): SharedPayload = SharedPayloadJson.decodeFromJsonElement(
    SharedSharedPayloadFromBuilder.serializer(), this
)

internal inline fun <reified T> T.encodeToJsonElement(): JsonElement =
    SharedPayloadJson.encodeToJsonElement(this)

//
//internal inline fun <reified T> T.encodeToJsonPrimitive(): JsonPrimitive =
//    SharedPayloadJson.encodeToJsonElement(this) as JsonPrimitive
//
//internal fun String.encodeToJsonElementFromString(): JsonElement =
//    SharedPayloadJson.parseToJsonElement(this)

