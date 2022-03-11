package org.apiplayground.apitest

import org.apiplayground.apitest.dtos.ApiRequest
import kotlinx.serialization.json.JsonPrimitive
import org.junit.jupiter.api.Test

internal class NewStratSer {

    @Test
    fun test() {
        val payload = buildSharedPayload {
            data(ApiRequest("Hello from api request as json element"))
        }
        println(payload.data)
        val jsonElement = payload.encodeToJson()
        println(jsonElement)

        val data = SharedPayload("Hello im json element")//.encodeToJson()
        println(data.data)
    }

    @Test
    fun test2() {
        val jsonObject = MyJsonObject("hi".encodeToJsonElement() as JsonPrimitive)
        println(jsonObject.value)
        val jsonElement = jsonObject.encodeToJsonElement()
        println(jsonElement)

    }
}

@kotlinx.serialization.Serializable
internal data class MyJsonObject(
    val value: JsonPrimitive = "Hello".encodeToJsonElement() as JsonPrimitive,
    val error: String = "Hi this is some errors"
)

