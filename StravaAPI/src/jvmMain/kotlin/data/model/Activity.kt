package data.data.model

import org.json.JSONObject

data class Activity(
    val id: String?,
    val stravaUserId: String,
    var select: Boolean,
    var json: JSONObject
)