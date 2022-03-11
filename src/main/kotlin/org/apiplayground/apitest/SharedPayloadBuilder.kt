package org.apiplayground.apitest


import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonElement
import org.apiplayground.apitest.erros.ApiError
import org.apiplayground.apitest.erros.cast
import org.apiplayground.apitest.erros.encodeToString
import org.springframework.http.HttpStatus

public sealed interface SharedPayloadBuilder {
    // this should not be nullable
    public fun data(value: JsonElement?)
    public fun error(value: ApiError)
    public fun httpStatus(status: HttpStatus)
    public fun headers(headers: HashMap<String, String>)
}

public inline fun buildSharedPayload(block: SharedPayloadBuilder.() -> Unit): SharedPayload {
    val builder = SharedSharedPayloadFromBuilder()
    try {
        builder.block()
        return builder.build()
    } catch (t: Throwable) {
        throw t
    }
}

public fun SharedPayloadBuilder.data(value: Data): Unit = data(value.value.encodeToJsonElement())
internal inline fun <reified T> SharedPayloadBuilder.data(value: T): Unit = data(value.encodeToJsonElement())

public fun SharedPayloadBuilder.error(value: ApiError): Unit = error(value.cast().encodeToString())


@PublishedApi
@Serializable
internal class SharedSharedPayloadFromBuilder : SharedPayloadBuilder, SharedPayload {
    @Transient
    private var hasData = false

    @Transient
    private var hasErrors = false

    @Transient
    private var hasHeaders = false

    @Transient
    private var hasStatus = false

    @Transient
    override var mHttpStatus: HttpStatus = HttpStatus.OK
        private set

    @Transient
    @Deprecated("No longer used.")
    private var mHeaders: HashMap<String, String> = hashMapOf()

    @Polymorphic
    override var data: JsonElement? = null
        private set

    override var error: ApiError? = null
        private set

    override fun data(value: JsonElement?) {
        if (hasData) {
            error("Data already provided")
        }
        data = value
        hasData = true
    }

    override fun error(value: ApiError) {
        if (hasErrors) {
            error("Error already provided")
        }
        error = value
        hasErrors = true
    }

    override fun httpStatus(status: HttpStatus) {
        if (hasStatus) {
            error("HttpStatus already provided")
        }
        mHttpStatus = status
        hasStatus = true
    }

    override fun headers(headers: HashMap<String, String>) {
        if (hasHeaders) {
            error("headers already provided")
        }
        mHeaders = headers
        hasHeaders = true
    }

    fun build(): SharedPayload {
        check(hasData || hasErrors) { "Data or error is required" }
        return this
    }
}