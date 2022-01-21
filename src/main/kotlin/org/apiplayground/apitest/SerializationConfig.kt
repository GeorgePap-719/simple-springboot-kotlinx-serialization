@file:OptIn(ExperimentalSerializationApi::class)

package org.apiplayground.apitest

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies

@Configuration
class SerializationConfig {

    @Bean
    fun json(): Json = Json

    @Bean
    fun kotlinSerializationJsonEncoder(json: Json): KotlinSerializationJsonEncoder =
        KotlinSerializationJsonEncoder(json)

    @Bean
    fun kotlinSerializationJsonDecoder(json: Json): KotlinSerializationJsonDecoder =
        KotlinSerializationJsonDecoder(json)


    @Bean
    fun exchangeStrategies(
        encoder: KotlinSerializationJsonEncoder,
        decoder: KotlinSerializationJsonDecoder
    ): ExchangeStrategies = ExchangeStrategies.builder().codecs { configurer ->
        configurer.defaultCodecs().apply {
            kotlinSerializationJsonEncoder(encoder)
            kotlinSerializationJsonDecoder(decoder)
        }
    }.build()

    @Bean
    fun webfluxConfigurer(
        encoder: KotlinSerializationJsonEncoder,
        decoder: KotlinSerializationJsonDecoder
    ): WebFluxConfigurer =
        object : WebFluxConfigurer {
            override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
                configurer.defaultCodecs().apply {
                    kotlinSerializationJsonEncoder(encoder)
                    kotlinSerializationJsonDecoder(decoder)
                }
            }
        }

}