
package org.apiplayground.apitest.request

import org.apiplayground.apitest.handler.Handler
import org.apiplayground.apitest.context.ActionContext
import org.apiplayground.apitest.payload.ApiResponse
import org.apiplayground.apitest.payload.SharedPayload

public class RequestHandlerBuilder(private val actionContext: ActionContext) : Request<ApiResponse> {
    private var hasResponse = false
    public var sharedPayload: SharedPayload? = null

    public override suspend fun requestResponse(
        handler: Handler<ApiResponse>,
        block: suspend () -> ApiResponse?
    ) {
        if (hasResponse) {
            error { "Already received response" }
        }
        handler.createApiResponse(actionContext, payload = block.invoke())
        hasResponse = true
    }


    internal fun build(): RequestHandlerBuilder =
        this
}

@Suppress("FunctionName")
public suspend fun RequestHandler(
    actionContext: ActionContext,
    block: suspend RequestHandlerBuilder.() -> Unit = {}
): RequestHandlerBuilder {
    val builder = RequestHandlerBuilder(actionContext)
    builder.block()
    return builder.build()
}



