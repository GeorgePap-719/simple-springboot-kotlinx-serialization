package org.apiplayground.apitest.dtos

import org.apiplayground.apitest.erros.ErrorAndCode
import org.apiplayground.apitest.erros.buildApiError
import org.springframework.http.HttpStatus

@Suppress("TestFunctionName")
internal fun ApiError(error: String, status: HttpStatus? = null) =
    status?.let {
        buildApiError(it) {
            exception(ErrorAndCode(error))
        }
    } ?: buildApiError { exception(ErrorAndCode(error, null)) }