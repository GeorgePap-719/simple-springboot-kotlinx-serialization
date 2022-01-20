package org.apiplayground.apitest.payload

import org.apiplayground.apitest.errors.ApiError
import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus


public fun SharedPayload(data: HashMap<String, String?> = hashMapOf(), error: ApiError? = null): SharedPayload =
    DefaultSharedPayload(data, error)

public sealed interface SharedPayload {
    public val data: Any?
    public val error: ApiError?
    public val mHttpStatus: HttpStatus
}


public fun SharedPayload.asString(): String = "data:$data, error:$error"

public interface PayloadReader<M : SharedPayload> {
    public fun read(): M
}

@Serializable
internal data class DefaultSharedPayload(
    override val data: HashMap<String, String?> = hashMapOf(),
    override val error: ApiError? = null,
    override val mHttpStatus: HttpStatus = HttpStatus.OK
) : SharedPayload






