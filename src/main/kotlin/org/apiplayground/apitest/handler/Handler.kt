package org.apiplayground.apitest.handler

import org.apiplayground.apitest.context.ActionContext
import org.apiplayground.apitest.payload.SharedPayload

public interface Handler<T : SharedPayload> {
    public fun createApiResponse(actionContext: ActionContext, payload: T?)
}
