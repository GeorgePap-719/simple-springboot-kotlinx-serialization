package org.apiplayground.apitest.handler

import org.apiplayground.apitest.context.ActionContext
import org.apiplayground.apitest.payload.ApiResponse
import org.springframework.stereotype.Component

@Component
class PingHandler : Handler<ApiResponse> {
    override fun createApiResponse(actionContext: ActionContext, payload: ApiResponse?) {
        actionContext.data = payload?.data
    }
}