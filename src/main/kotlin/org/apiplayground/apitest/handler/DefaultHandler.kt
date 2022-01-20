package org.apiplayground.apitest.handler

import org.apiplayground.apitest.ApiActionStatus.OperationFailedStatus
import org.apiplayground.apitest.context.ActionContext
import org.apiplayground.apitest.errors.DefaultApiError
import org.apiplayground.apitest.errors.ErrorAndCode
import org.apiplayground.apitest.payload.ApiResponse
import org.springframework.http.HttpStatus


public abstract class DefaultHandler : Handler<ApiResponse> {

    override fun createApiResponse(actionContext: ActionContext, payload: ApiResponse?) {
        if (payload == null) {
            actionContext.data = null
            actionContext.error = DefaultApiError(HttpStatus.NOT_FOUND, ErrorAndCode("404", "Not Found"))
            actionContext.actionStatus = OperationFailedStatus(HttpStatus.NOT_FOUND)
        } else {
            actionContext.data = payload.data
            actionContext.error = payload.error
            if (payload.error != null)
                actionContext.actionStatus = actionContext.onFailureStatus
        }
    }
}


