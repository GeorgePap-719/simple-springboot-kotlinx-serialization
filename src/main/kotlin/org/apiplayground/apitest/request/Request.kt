package org.apiplayground.apitest.request

import org.apiplayground.apitest.handler.Handler
import org.apiplayground.apitest.payload.SharedPayload


public interface Request<T : SharedPayload> {
    /**
     * Main api that handles requests and responses
     * for public APIs.
     *
     * Returns [T] with upper bound [SharedPayload].
     */
    public suspend fun requestResponse(
        handler: Handler<T>,
        block: suspend () -> T?
    )
}

