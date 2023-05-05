package data

import data.data.AuthenticationManager
import data.data.model.Activity
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

//2 Acts
const val client_id = "105822"
const val client_secret = "e549180fe8992b629caffa80702bd9339759eff7"
const val grant_type = "authorization_code"
const val scope = "read_all"
const val auth_domain = "www.strava.com/oauth"

/*
//20+ Acts
const val code = "ae948aa85162f804fe6c77b5517b9b4e99e83988"

const val client_id = "105658"
const val client_secret = "0ef67acd7643ec1c367d83a347394bb2cd1efee6"
const val grant_type = "authorization_code"
https://www.strava.com/oauth/authorize?client_id=105658&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=activity:read_all
*/
const val url = "https://www.strava.com/oauth/token"
var ActivityList = mutableListOf<Activity>()
var accessToken = ""

fun GetAccessToken() {
    val code = GetAuthCode()
    val postData = "client_id=$client_id&client_secret=$client_secret&code=$code&grant_type=$grant_type"
    val urlObj = URL(url)
    val conn = urlObj.openConnection() as HttpURLConnection

    conn.doOutput = true
    conn.requestMethod = "POST"
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
    conn.setRequestProperty("charset", "utf-8")
    conn.setRequestProperty("Content-Length", postData.length.toString())
    conn.useCaches = false

    val wr = conn.outputStream
    wr.write(postData.toByteArray(charset("UTF-8")))
    wr.close()

    val responseCode = conn.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val inputStream = BufferedReader(InputStreamReader(conn.inputStream))
        val response = StringBuffer()

        var inputLine: String?
        while (inputStream.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        inputStream.close()

        val jsonResponse = JSONObject(response.toString())
        accessToken = jsonResponse.getString("access_token")


        println("Access token: $accessToken")
    } else {
        println("POST request to URL : $url failed with HTTP status code : $responseCode")
    }
}

fun GetEpochTime(date: LocalDate): Long {
    return date.toEpochSecond(LocalTime.now(), ZoneOffset.UTC) // convert to seconds
}

fun GetAuthCode(): String {
    return AuthenticationManager().authenticateUser(
        auth_domain,
        client_id,
        scope
    )
}


fun GetActivityIds(): List<String> {
    val givenDate = LocalDate.of(2023, 3, 1)
    val url = "https://www.strava.com/api/v3/athlete/activities?after=${GetEpochTime(givenDate)}&per_page=5"
    val urlObj = URL(url)
    val conn = urlObj.openConnection() as HttpURLConnection
    val activityIds = mutableListOf<String>()
    conn.requestMethod = "GET"
    conn.setRequestProperty("Authorization", "Bearer $accessToken")

    val responseCode = conn.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val inputStream = BufferedReader(InputStreamReader(conn.inputStream))
        val response = StringBuffer()
        var inputLine: String?
        while (inputStream.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }

        inputStream.close()

        val jsonArray = JSONArray(response.toString())
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val activityId = jsonObject.getLong("id")
            activityIds.add(activityId.toString())
        }

        return activityIds
    } else {
        println("GET request to URL : $url failed with HTTP status code : $responseCode")
        return emptyList()
    }
}

fun GetActivityList(id: String) {
    val url = "https://www.strava.com/api/v3/activities/$id"
    val urlObj = URL(url)
    val conn = urlObj.openConnection() as HttpURLConnection
    conn.requestMethod = "GET"
    conn.setRequestProperty("Authorization", "Bearer $accessToken")

    val responseCode = conn.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val inputStream = BufferedReader(InputStreamReader(conn.inputStream))
        val response = StringBuffer()
        var inputLine: String?
        while (inputStream.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }

        inputStream.close()
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val jsonArray = JSONArray("[$response]")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.getLong("id").toString() == id) {
                ActivityList.add(
                    Activity(
                        id = id,
                        name = jsonObject.getString("name"),
                        distance = jsonObject.getDouble("distance"),
                        type = jsonObject.getString("type"),
                        date = LocalDateTime.parse(jsonObject.getString("start_date_local"), formatter)
                    )
                )
            }
        }
    }
}