package org.apiplayground.apitest


import org.apiplayground.apitest.erros.ApiError
import kotlinx.serialization.json.JsonElement
import org.springframework.http.HttpStatus

public sealed interface SharedPayload {
    public val data: JsonElement?

    public val error: ApiError?

    public val mHttpStatus: HttpStatus

    public companion object {
        public val Empty: SharedPayload = EmptySharedPayload()
    }
}

@Suppress("FunctionName")
internal fun EmptySharedPayload(
    data: String? = null,
    error: ApiError? = null,
    mHttpStatus: HttpStatus = HttpStatus.OK,
    mHeaders: HashMap<String, String> = HashMap()
): SharedPayload = buildSharedPayload {
    data?.let { data(data) }
    error?.let { error(it) }
    headers(mHeaders)
    httpStatus(mHttpStatus)
}


public fun SharedPayload(
    data: Data,
    error: ApiError? = null,
    mHttpStatus: HttpStatus = HttpStatus.OK,
    mHeaders: HashMap<String, String> = HashMap()
): SharedPayload = buildSharedPayload {
    data(data)
    error?.let { error(it) }
    headers(mHeaders)
    httpStatus(mHttpStatus)
}


public fun SharedPayload(
    data: String? = null,
    error: ApiError? = null,
    mHttpStatus: HttpStatus = HttpStatus.OK,
    mHeaders: HashMap<String, String> = HashMap()
): SharedPayload = buildSharedPayload {
    data?.let { data(it) }
    error?.let { error(it) }
    headers(mHeaders)
    httpStatus(mHttpStatus)
}

public fun SharedPayload(
    data: Any? = null,
    error: ApiError? = null,
    mHttpStatus: HttpStatus = HttpStatus.OK,
    mHeaders: HashMap<String, String> = HashMap()
): SharedPayload = buildSharedPayload {
    data?.let { data(it) }
    error?.let { error(it) }
    headers(mHeaders)
    httpStatus(mHttpStatus)
}






















