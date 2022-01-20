package org.apiplayground.apitest

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apiplayground.apitest.context.ActionContextHandler
import org.apiplayground.apitest.handler.PingHandler
import org.apiplayground.apitest.payload.ApiResponse
import org.apiplayground.apitest.request.RequestHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ping")
class Api(private val pingHandler: PingHandler) {
    val logger = logger()


    @GetMapping(value = ["test", "/test"])
    suspend fun test(response: ServerHttpResponse): FakeResponse {
        val fakeResponse = FakeResponse("Hello From test")
        logger.debug(Json.encodeToString(fakeResponse))
        return fakeResponse
    }


    @GetMapping(value = ["", "/"])
    suspend fun ping(request: ServerHttpRequest, response: ServerHttpResponse): ApiResponse =
        ActionContextHandler {
            configureForAction()
            prepareContext(request, response)

            RequestHandler(this) {
                val jsonObject = "{\"message\":\"Ping process done\",\"status\":\"success\"}"
                requestResponse(pingHandler) {
                    ApiResponse(data = jsonObject, mHttpStatus = HttpStatus.ACCEPTED)
                }
            }
        }


}

@Serializable
class FakeResponse(val data: String, val error: String? = null)

// when needing to instantiate a logger for a class, do this:
// private val logger = logger()
@Suppress("unused")
inline fun <reified T> T.logger(): Logger =
    if (T::class.isCompanion)
        LoggerFactory.getLogger(T::class.java.enclosingClass)
    else
        LoggerFactory.getLogger(T::class.java)

// to write to metric log do this:
// logger.metric("message")
fun Logger.metric(msg: String) {
    this.info(MarkerFactory.getMarker("METRIC"), msg)
}