package org.apiplayground.apitest.context

import org.apiplayground.apitest.payload.ApiResponse

/**
 * Main handler for serving the context based on the [ApiActionStatus].
 *
 * Returns [ApiResponse].
 */
@Suppress("FunctionName")
public suspend fun ActionContextHandler(configure: suspend ActionContext.() -> Unit): ApiResponse {
    val builder = ActionContext()
    try {
        builder.configure()
        return builder.getResponse()
    } catch (t: Throwable) {
        throw t
    }
}
