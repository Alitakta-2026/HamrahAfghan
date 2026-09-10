package com.hamrahafghan.app.network

import java.net.URL
import javax.net.ssl.HttpsURLConnection

object NetworkClient {
    fun getTestData(): String {
        val connection = URL("https://httpbin.org/get").openConnection() as HttpsURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 10000
        connection.readTimeout = 10000

        return try {
            connection.inputStream.bufferedReader().use { it.readText() }
        } finally {
            connection.disconnect()
        }
    }
}
