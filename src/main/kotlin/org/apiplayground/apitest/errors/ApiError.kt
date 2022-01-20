package org.apiplayground.apitest.errors

import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus


public sealed interface ApiError {
    public val status: HttpStatus
    public val exception: ErrorAndCode?
    public val causes: MutableList<ErrorAndCode>?
}

@Serializable
public data class DefaultApiError(
    override val status: HttpStatus = HttpStatus.OK,
    override val exception: ErrorAndCode? = ErrorAndCode("1", "OK"),
    override val causes: MutableList<ErrorAndCode>? = null
) : ApiError