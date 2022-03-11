package org.apiplayground.apitest


import org.apiplayground.apitest.erros.ApiError
import kotlinx.serialization.json.JsonElement
import org.springframework.http.HttpStatus

// add reader for data and error, #115
public sealed interface SharedPayload {
    // this should not be nullable
    public val data: JsonElement?

    // this should not be nullable
    public val error: ApiError?

    @Deprecated(
        "Http status is no longer set through SharedPayload. It can be set " +
                "in ActionContext through ActionContextConfigurer.",
        level = DeprecationLevel.WARNING
    )
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

/**
 * [SharedPayload] factory function with [Data], [ApiError], [HttpStatus] and
 * a [HashMap] as headers. Has almost no overhead over call to [SharedPayload]
 * constructor.
 *
 * It's recommended to always construct [Data] through the provided extensions
 * [encodeToData] and [decodeFromData], otherwise the serializer will not have
 * any information to infer the original object when it's needed to be decoded
 * to its original format.
 */
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

/**
 * [SharedPayload] factory function with [String], [ApiError], [HttpStatus] and
 * a [HashMap] as headers. Has almost no overhead over call to [SharedPayload]
 * constructor.
 *
 * It's recommended to encode the object into a string and to not simply call
 * the .toString() extension, since if the object has non-trivial structure there
 * is no easy way to deconstruct it back to its original format.
 *
 * For convenience, there are provided [encodeToData] and [decodeFromData] extensions,
 * for easy construction and deconstruction of any custom objects.
 */
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






















