package org.apiplayground.apitest.erros

import org.springframework.http.HttpStatus

public sealed interface ApiError {
    public val status: HttpStatus
    public val exception: ErrorAndCode?
    public val causes: MutableList<ErrorAndCode>?
}


internal fun ApiError.cast(): ApiErrorFromBuilder = this as ApiErrorFromBuilder