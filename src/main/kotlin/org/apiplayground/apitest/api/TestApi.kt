package org.apiplayground.apitest.api

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apiplayground.apitest.FakeResponse
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestApi {


    @GetMapping(value = ["test", "/test"])
    suspend fun test(response: ServerHttpResponse): FakeResponse = coroutineScope {
        val responseDeferred = async {
            return@async acceptResponse()
        }
        return@coroutineScope responseDeferred.await()
    }

    @GetMapping(value = ["test2", "/test2"])
    suspend fun test2(response: ServerHttpResponse): FakeResponse {

        val fakeResponseDeferred = acceptResponseAsync()

        return fakeResponseDeferred.await()
    }

    @GetMapping(value = ["test2", "/test2"])
    suspend fun test3(response: ServerHttpResponse): FakeResponse {

        val fakeResponseDeferred = acceptDeferredResponse()

        return fakeResponseDeferred
    }


    suspend fun acceptResponse(): FakeResponse {
        return FakeResponse("Hello From test")
    }

    suspend fun acceptResponseAsync(): Deferred<FakeResponse> = coroutineScope {
        async {
            return@async FakeResponse("Hello async From test")
        }
    }

    suspend fun acceptDeferredResponse(): FakeResponse = coroutineScope {
        async {
            return@async FakeResponse("Hello pretend we are doing some async heavy stuff")
        }
    }.await()

}