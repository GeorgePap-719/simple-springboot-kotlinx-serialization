package org.apiplayground.apitest.dtos

@kotlinx.serialization.Serializable
internal data class ApiRequest(val value: String = "some value", val error: String = "some errors")