package org.apiplayground.apitest

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@JvmInline
public value class Data(public val value: String)


/**
 * Generic [Json] serializer and returns [Data].
 */
public inline fun <reified T> T.encodeToData(): Data = Data(Json.encodeToString(this))

/**
 * Generic [Json] deserializer for [Data].
 */
public inline fun <reified T> Data.decodeFromData(): T = Json.decodeFromString(this.value)