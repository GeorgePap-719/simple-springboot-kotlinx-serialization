package org.apiplayground.apitest.erros

import kotlinx.serialization.Serializable

@Serializable
public data class ErrorAndCode(
    val message: String?,
    val code: String? = null
)
