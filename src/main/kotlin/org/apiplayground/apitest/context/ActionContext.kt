package org.apiplayground.apitest.context



import org.apiplayground.apitest.ApiActionStatus
import org.apiplayground.apitest.ApiActionStatus.*
import org.apiplayground.apitest.security.ISpringCookieProvider
import org.apiplayground.apitest.security.ISpringHeaderProvider
import org.apiplayground.apitest.errors.ApiError
import org.apiplayground.apitest.payload.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse


public class ActionContext {
    public var data: Any? = null
    public var error: ApiError? = null
    private var contextHasBeenSet = false
    public val headers: MutableList<ISpringHeaderProvider> = mutableListOf()
    public val cookies: MutableList<ISpringCookieProvider> = mutableListOf()

    public var actionStatus: ApiActionStatus = OperationSuccessStatus()
    public var onSuccessStatus: OperationSuccessStatus = OperationSuccessStatus()
    public var onFailureStatus: OperationFailedStatus = OperationFailedStatus()

    private var apiResponse: ApiResponse? = null
    private lateinit var httpRequest: ServerHttpRequest
    private lateinit var httpResponse: ServerHttpResponse

    // Config API
    public fun configureForSingleEntityReadUpdate() {
        onFailureStatus = OperationFailedStatus(HttpStatus.NOT_FOUND)
        actionStatus
    }

    public fun configureForEntityCreation() {
        onSuccessStatus = OperationSuccessStatus(HttpStatus.CREATED)
        onFailureStatus = OperationFailedStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    public fun configureForEntityDeletion() {
        onSuccessStatus = OperationSuccessStatus(HttpStatus.NO_CONTENT)
        onFailureStatus = OperationFailedStatus(HttpStatus.NOT_FOUND)
    }

    public fun configureForAction() {
        onSuccessStatus = OperationSuccessStatus(HttpStatus.ACCEPTED)
    }

    public fun configureForLogin() {
        onSuccessStatus = OperationSuccessStatus(HttpStatus.OK)
        onFailureStatus = OperationFailedStatus(HttpStatus.UNAUTHORIZED)
    }

    public fun configureForWebHookStatus() {
        onFailureStatus = OperationFailedStatus(HttpStatus.NOT_FOUND)
    }

    public fun getResponse(): ApiResponse {
        createResponse()
        apiResponse?.let {
            return@getResponse it
        } ?: error {
            "api response is null"
        }
    }

    public fun prepareContext(
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ) {
        if (contextHasBeenSet) error { "Context has been set already" }
        httpRequest = request
        httpResponse = response
        contextHasBeenSet = true
    }

    private fun createResponse() {
        if (apiResponse == null) {
            apiResponse = ApiResponse(
                data = data,
                error = error
            )
            when (actionStatus) {
                is OperationSuccessStatus -> httpResponse.statusCode = onSuccessStatus.value
                is OperationFailedStatus -> httpResponse.statusCode = onFailureStatus.value
                else -> httpResponse.statusCode = actionStatus.value
            }
        }
    }

}