@file:OptIn(InternalSerializationApi::class)

package org.apiplayground.apitest

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import org.apiplayground.apitest.handler.PingHandler
import org.apiplayground.apitest.payload.ApiResponse
import org.apiplayground.apitest.payload.SharedPayload
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest
@Import(Config::class)
class PingTest(@Autowired val client: WebTestClient) {


    @Autowired
    private lateinit var pingHandler: PingHandler


    @Test
    fun `test simple ping`() {
        val jsonObject = "{\"message\":\"Ping process done\",\"status\":\"success\"}"
        val response = ApiResponse(data = jsonObject, mHttpStatus = HttpStatus.ACCEPTED)

        client.get().uri("/api/v1/ping").exchange().expectStatus().isAccepted
            .expectBody<ApiResponse>().isEqualTo(response)
    }
}


@Configuration
class Config {

    @Bean
    public fun pingHandler(): PingHandler = PingHandler()

}