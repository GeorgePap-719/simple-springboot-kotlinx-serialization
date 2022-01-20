package org.apiplayground.apitest.payload

import kotlinx.serialization.Contextual
import org.apiplayground.apitest.errors.ApiError
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.apiplayground.apitest.AnySerializer
import org.springframework.http.HttpStatus

public sealed interface SharedPayloadBuilder {
    public fun data(value: Any)
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

    private var mHeaders: HashMap<String, String> = hashMapOf()

    @Serializable(with = AnySerializer::class)
    override var data: Any? = hashMapOf<String, String?>()
        private set
    override var error: ApiError? = null
        private set
    override var mHttpStatus: HttpStatus = HttpStatus.OK
        private set

    override fun data(value: Any) {
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
        check(hasData) { "Data is required" }
        return this
    }
}