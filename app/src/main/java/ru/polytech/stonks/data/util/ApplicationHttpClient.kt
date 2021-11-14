package ru.polytech.stonks.data.util

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationHttpClient @Inject constructor() {

    val client = HttpClient(Apache) {
        engine {
            followRedirects = true
            socketTimeout = 10_000
            connectTimeout = 10_000
            connectionRequestTimeout = 20_000
        }

        defaultRequest {
            url {
                host = "localhost:8080/"
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}