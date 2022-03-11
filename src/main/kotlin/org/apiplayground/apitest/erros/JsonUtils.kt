package org.apiplayground.apitest.erros

import kotlinx.serialization.json.Json
import org.apiplayground.apitest.erros.ApiError
import org.apiplayground.apitest.erros.ApiErrorFromBuilder


internal fun ApiErrorFromBuilder.encodeToString(): String =
    Json.encodeToString(ApiErrorFromBuilder.serializer(), this)


public fun String.decodeToApiError(): ApiError =
    Json.decodeFromString(ApiErrorFromBuilder.serializer(), this)