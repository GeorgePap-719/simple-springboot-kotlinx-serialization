
package org.apiplayground.apitest


import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.apiplayground.apitest.payload.ApiResponse
import org.apiplayground.apitest.payload.DefaultSharedPayload
import org.apiplayground.apitest.payload.SharedPayload
import org.apiplayground.apitest.payload.SharedSharedPayloadFromBuilder


public val sharedPayloadModule: SerializersModule = SerializersModule {
    polymorphic(SharedPayload::class) {
        subclass(SharedSharedPayloadFromBuilder::class, SharedSharedPayloadFromBuilder.serializer())
        subclass(DefaultSharedPayload::class, DefaultSharedPayload.serializer())
        polymorphic(Any::class) {
            subclass(SharedSharedPayloadFromBuilder::class, SharedSharedPayloadFromBuilder.serializer())
        }
    }
}

public val apiResponseModule: SerializersModule = SerializersModule {
    polymorphic(ApiResponse::class) {
        subclass(SharedSharedPayloadFromBuilder::class, SharedSharedPayloadFromBuilder.serializer())
        subclass(DefaultSharedPayload::class, DefaultSharedPayload.serializer())
        polymorphic(Any::class) {
            subclass(SharedSharedPayloadFromBuilder::class, SharedSharedPayloadFromBuilder.serializer())
        }
    }
}

public val SharedPayloadJson: Json = Json { serializersModule = sharedPayloadModule }

