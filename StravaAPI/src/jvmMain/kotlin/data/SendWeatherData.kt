package data.data

import data.data.model.Weather
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

fun sendWeatherData(weather: Weather) {

    val client = OkHttpClient()

    val json = """
    {
        "location": "${weather.city}",
        "temperature": "${weather.temperature}",
        "cloudiness": "${weather.aproxCloud}",
        "humidity": "${weather.humidity}"
    }
    """.trimIndent()

    val body = json.toRequestBody("application/json".toMediaType())
    val request = Request.Builder()
        .url("http://localhost:3001/weather")
        .post(body)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
    }
}