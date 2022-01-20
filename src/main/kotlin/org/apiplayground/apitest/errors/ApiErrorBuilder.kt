package org.apiplayground.apitest.errors

import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus

public sealed interface ApiErrorBuilder {
    public fun exception(exception: ErrorAndCode?)
    public fun cause(causes: MutableList<ErrorAndCode>?)
}

public inline fun buildApiError(status: HttpStatus = HttpStatus.OK, block: ApiErrorBuilder.() -> Unit): ApiError {
    val builder = ApiErrorFromBuilder(status)
    try {
        builder.block()
        return builder.build()
    } catch (t: Throwable) {
        throw t
    }
}

@PublishedApi
@Serializable
internal class ApiErrorFromBuilder(
    override val status: HttpStatus,
) : ApiErrorBuilder, ApiError {
    private var hasExceptions = false
    override var exception: ErrorAndCode? = null
        private set
    override var causes: MutableList<ErrorAndCode>? = null
        private set

    override fun exception(exception: ErrorAndCode?) {
        if (hasExceptions) {
            error { "Already provided exceptions" }
        }
        this.exception = exception
        hasExceptions = true
    }

    override fun cause(causes: MutableList<ErrorAndCode>?) {
        this.causes = causes
    }

    fun build(): ApiError {
        check(hasExceptions) { "Can not create empty errors" }
        return this
    }

}