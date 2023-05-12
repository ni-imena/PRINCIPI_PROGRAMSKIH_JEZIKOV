package data.data

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject

fun sendData(userId: String, activity: JSONObject, stream: JSONObject?) {

    val client = OkHttpClient()

    val activityStr = activity.toString().replace("\"", "\\\"") // Escape double quotes
    val streamStr = stream?.toString()?.replace("\"", "\\\"") // Escape double quotes and handle null


    val json = """
    {
        "stravaUserId": "$userId",
        "activity": "$activityStr",
        "stream": "$streamStr"
    }
    """.trimIndent()

    val body = json.toRequestBody("application/json".toMediaType())

    val request = Request.Builder()
        .url("http://localhost:3001/runs")
        .post(body)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
    }

}