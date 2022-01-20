package org.apiplayground.apitest.errors

import kotlinx.serialization.Serializable

@Serializable
public data class ErrorAndCode(
    val message: String?,
    val code: String?
)
