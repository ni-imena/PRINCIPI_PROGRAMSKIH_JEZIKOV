package data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.data.sendData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun IntroScreen() {
    var isParserClicked by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Header()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            if (isParserClicked) {
                GenerateData()
                Body()

            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Footer(
                onParseClick = { isParserClicked = true },
                onSaveClick = { SaveData() }
            )
        }
    }
}

fun GenerateData() {
    /*
    val act1 = StringBuffer(
        "{\"resource_state\":3,\"athlete\":{\"id\":116468534,\"resource_state\":1},\"name\":\"TestWalk1\",\"distance\":9.7,\"moving_time\":28,\"elapsed_time\":28,\"total_elevation_gain\":0.0,\"type\":\"Walk\",\"sport_type\":\"Walk\",\"id\":8906239232,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"timezone\":\"(GMT+01:00) Europe/Ljubljana\",\"utc_offset\":7200.0,\"location_city\":null,\"location_state\":null,\"location_country\":null,\"achievement_count\":0,\"kudos_count\":0,\"comment_count\":0,\"athlete_count\":1,\"photo_count\":0,\"map\":{\"id\":\"a8906239232\",\"polyline\":\"}md{Germ~A@CDJG@?A\",\"resource_state\":3,\"summary_polyline\":\"}md{Germ~A@CDJG@?A\"},\"trainer\":false,\"commute\":false,\"manual\":false,\"private\":false,\"visibility\":\"everyone\",\"flagged\":false,\"gear_id\":null,\"start_latlng\":[46.55855353921652,15.639551980420947],\"end_latlng\":[46.55855278484523,15.639512753114104],\"average_speed\":0.346,\"max_speed\":1.143,\"average_cadence\":53.0,\"has_heartrate\":false,\"heartrate_opt_out\":false,\"display_hide_heartrate_option\":false,\"elev_high\":270.7,\"elev_low\":270.6,\"upload_id\":9556445684,\"upload_id_str\":\"9556445684\",\"external_id\":\"379c3765-799a-4e89-9e2c-94ca96c1de9f-activity.fit\",\"from_accepted_tag\":false,\"pr_count\":0,\"total_photo_count\":0,\"has_kudoed\":false,\"description\":null,\"calories\":0,\"perceived_exertion\":null,\"prefer_perceived_exertion\":false,\"segment_efforts\":[],\"splits_metric\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"splits_standard\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"laps\":[{\"id\":30263608009,\"resource_state\":2,\"name\":\"Lap 1\",\"activity\":{\"id\":8906239232,\"resource_state\":1},\"athlete\":{\"id\":116468534,\"resource_state\":1},\"elapsed_time\":33,\"moving_time\":33,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"distance\":9.69,\"start_index\":0,\"end_index\":14,\"total_elevation_gain\":0.0,\"average_speed\":0.29,\"max_speed\":1.143,\"average_cadence\":53.0,\"device_watts\":false,\"lap_index\":1,\"split\":1}],\"photos\":{\"primary\":null,\"count\":0},\"stats_visibility\":[{\"type\":\"heart_rate\",\"visibility\":\"everyone\"},{\"type\":\"pace\",\"visibility\":\"everyone\"},{\"type\":\"power\",\"visibility\":\"everyone\"},{\"type\":\"speed\",\"visibility\":\"everyone\"},{\"type\":\"calories\",\"visibility\":\"everyone\"}],\"hide_from_home\":false,\"device_name\":\"Strava Android App\",\"embed_token\":\"6de18481da7f53c27ac4029fd375a37fb027a807\",\"available_zones\":[]}"
    )
    val act2 = StringBuffer(
        "{\"resource_state\":3,\"athlete\":{\"id\":116468534,\"resource_state\":1},\"name\":\"TestWalk1\",\"distance\":9.7,\"moving_time\":28,\"elapsed_time\":28,\"total_elevation_gain\":0.0,\"type\":\"Walk\",\"sport_type\":\"Walk\",\"id\":8906239232,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"timezone\":\"(GMT+01:00) Europe/Ljubljana\",\"utc_offset\":7200.0,\"location_city\":null,\"location_state\":null,\"location_country\":null,\"achievement_count\":0,\"kudos_count\":0,\"comment_count\":0,\"athlete_count\":1,\"photo_count\":0,\"map\":{\"id\":\"a8906239232\",\"polyline\":\"}md{Germ~A@CDJG@?A\",\"resource_state\":3,\"summary_polyline\":\"}md{Germ~A@CDJG@?A\"},\"trainer\":false,\"commute\":false,\"manual\":false,\"private\":false,\"visibility\":\"everyone\",\"flagged\":false,\"gear_id\":null,\"start_latlng\":[46.55855353921652,15.639551980420947],\"end_latlng\":[46.55855278484523,15.639512753114104],\"average_speed\":0.346,\"max_speed\":1.143,\"average_cadence\":53.0,\"has_heartrate\":false,\"heartrate_opt_out\":false,\"display_hide_heartrate_option\":false,\"elev_high\":270.7,\"elev_low\":270.6,\"upload_id\":9556445684,\"upload_id_str\":\"9556445684\",\"external_id\":\"379c3765-799a-4e89-9e2c-94ca96c1de9f-activity.fit\",\"from_accepted_tag\":false,\"pr_count\":0,\"total_photo_count\":0,\"has_kudoed\":false,\"description\":null,\"calories\":0,\"perceived_exertion\":null,\"prefer_perceived_exertion\":false,\"segment_efforts\":[],\"splits_metric\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"splits_standard\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"laps\":[{\"id\":30263608009,\"resource_state\":2,\"name\":\"Lap 1\",\"activity\":{\"id\":8906239232,\"resource_state\":1},\"athlete\":{\"id\":116468534,\"resource_state\":1},\"elapsed_time\":33,\"moving_time\":33,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"distance\":9.69,\"start_index\":0,\"end_index\":14,\"total_elevation_gain\":0.0,\"average_speed\":0.29,\"max_speed\":1.143,\"average_cadence\":53.0,\"device_watts\":false,\"lap_index\":1,\"split\":1}],\"photos\":{\"primary\":null,\"count\":0},\"stats_visibility\":[{\"type\":\"heart_rate\",\"visibility\":\"everyone\"},{\"type\":\"pace\",\"visibility\":\"everyone\"},{\"type\":\"power\",\"visibility\":\"everyone\"},{\"type\":\"speed\",\"visibility\":\"everyone\"},{\"type\":\"calories\",\"visibility\":\"everyone\"}],\"hide_from_home\":false,\"device_name\":\"Strava Android App\",\"embed_token\":\"6de18481da7f53c27ac4029fd375a37fb027a807\",\"available_zones\":[]}"
    )
    val act3 = StringBuffer(
        "{\"resource_state\":3,\"athlete\":{\"id\":116468534,\"resource_state\":1},\"name\":\"TestWalk1\",\"distance\":9.7,\"moving_time\":28,\"elapsed_time\":28,\"total_elevation_gain\":0.0,\"type\":\"Walk\",\"sport_type\":\"Walk\",\"id\":8906239232,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"timezone\":\"(GMT+01:00) Europe/Ljubljana\",\"utc_offset\":7200.0,\"location_city\":null,\"location_state\":null,\"location_country\":null,\"achievement_count\":0,\"kudos_count\":0,\"comment_count\":0,\"athlete_count\":1,\"photo_count\":0,\"map\":{\"id\":\"a8906239232\",\"polyline\":\"}md{Germ~A@CDJG@?A\",\"resource_state\":3,\"summary_polyline\":\"}md{Germ~A@CDJG@?A\"},\"trainer\":false,\"commute\":false,\"manual\":false,\"private\":false,\"visibility\":\"everyone\",\"flagged\":false,\"gear_id\":null,\"start_latlng\":[46.55855353921652,15.639551980420947],\"end_latlng\":[46.55855278484523,15.639512753114104],\"average_speed\":0.346,\"max_speed\":1.143,\"average_cadence\":53.0,\"has_heartrate\":false,\"heartrate_opt_out\":false,\"display_hide_heartrate_option\":false,\"elev_high\":270.7,\"elev_low\":270.6,\"upload_id\":9556445684,\"upload_id_str\":\"9556445684\",\"external_id\":\"379c3765-799a-4e89-9e2c-94ca96c1de9f-activity.fit\",\"from_accepted_tag\":false,\"pr_count\":0,\"total_photo_count\":0,\"has_kudoed\":false,\"description\":null,\"calories\":0,\"perceived_exertion\":null,\"prefer_perceived_exertion\":false,\"segment_efforts\":[],\"splits_metric\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"splits_standard\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"laps\":[{\"id\":30263608009,\"resource_state\":2,\"name\":\"Lap 1\",\"activity\":{\"id\":8906239232,\"resource_state\":1},\"athlete\":{\"id\":116468534,\"resource_state\":1},\"elapsed_time\":33,\"moving_time\":33,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"distance\":9.69,\"start_index\":0,\"end_index\":14,\"total_elevation_gain\":0.0,\"average_speed\":0.29,\"max_speed\":1.143,\"average_cadence\":53.0,\"device_watts\":false,\"lap_index\":1,\"split\":1}],\"photos\":{\"primary\":null,\"count\":0},\"stats_visibility\":[{\"type\":\"heart_rate\",\"visibility\":\"everyone\"},{\"type\":\"pace\",\"visibility\":\"everyone\"},{\"type\":\"power\",\"visibility\":\"everyone\"},{\"type\":\"speed\",\"visibility\":\"everyone\"},{\"type\":\"calories\",\"visibility\":\"everyone\"}],\"hide_from_home\":false,\"device_name\":\"Strava Android App\",\"embed_token\":\"6de18481da7f53c27ac4029fd375a37fb027a807\",\"available_zones\":[]}"
    )

    val jsonObject1 = JSONObject(act1.toString())
    val jsonObject2 = JSONObject(act2.toString())
    val jsonObject3 = JSONObject(act3.toString())

    ActivityList.add(Activity(null, false, jsonObject1))
    ActivityList.add(Activity(null, false, jsonObject2))
    ActivityList.add(Activity(null, false, jsonObject3))
*/

    GetAccessToken()
    val activ = GetActivityIds()
    for (act in activ) {
        GetActivityList(act)
    }
}

fun SaveData() {
    for (item in ActivityList) {
        if (item.select) {
                sendData(item.stravaUserId, item.json, GetActivityStream(item.json.getLong("id").toString()))
        }
    }
}

@Composable
fun Header() {
    Surface(
        color = Color(0xFF2962FF),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Activities",
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun Footer(onParseClick: () -> Unit, onSaveClick: () -> Unit) {
    var isGetButtonPressed by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .height(48.dp)
                .padding(start = 24.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (isGetButtonPressed) {
                        onSaveClick()
                    } else {
                        onParseClick()
                    }
                    isGetButtonPressed = true
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isGetButtonPressed) Color.Green else Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = if (isGetButtonPressed) "Save Data" else "Get Data")
            }
        }
    }
}

@Composable
fun Body() {
    val (activities, setActivities) = remember { mutableStateOf(ActivityList) }
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            items(activities) { activity ->
                val jsonObject = activity.json

                val name = jsonObject.getString("name")
                val type = jsonObject.getString("type")
                val date =
                    LocalDateTime.parse(jsonObject.getString("start_date_local"), DateTimeFormatter.ISO_DATE_TIME)

                var isEditing by remember { mutableStateOf(false) }
                var editedName by remember { mutableStateOf(name) }
                var editedType by remember { mutableStateOf(type) }
                var isSelected by remember { mutableStateOf(activity.select) }


                if (isEditing) {
                    Card(
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                modifier = Modifier.weight(1f)
                                    .padding(end = 8.dp),
                                value = editedName,
                                onValueChange = { editedName = it },
                                label = { Text("Name") }
                            )
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = editedType,
                                onValueChange = { editedType = it },
                                label = { Text("Type") }
                            )
                            Row {
                                IconButton(
                                    onClick = {
                                        jsonObject.put("name", editedName)
                                        jsonObject.put("type", editedType)
                                        activity.json = jsonObject
                                        isEditing = false
                                        println("After Save: $ActivityList")
                                    }
                                ) {
                                    Icon(Icons.Filled.Check, contentDescription = "Save")
                                }
                                IconButton(
                                    onClick = {
                                        isEditing = false
                                        println("After Cancel: $ActivityList")
                                    }
                                ) {
                                    Icon(Icons.Filled.Close, contentDescription = "Cancel")
                                }
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                Modifier
                                    .width(55.dp) // Add this line to set a fixed width
                            ) {
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = {
                                        isSelected = it
                                        activity.select = isSelected
                                    },
                                )
                            }

                            Column(
                                Modifier.weight(0.9f)
                            ) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.h6
                                )
                                Text(
                                    text = type,
                                    style = MaterialTheme.typography.subtitle2
                                )
                                Text(
                                    text = date.toString(),
                                    style = MaterialTheme.typography.subtitle2
                                )
                            }
                            Row {
                                IconButton(
                                    onClick = { isEditing = true }
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                                }
                                IconButton(
                                    onClick = {
                                        println("Before Delete: $ActivityList")
                                        val newActivities = activities.filter { it.id != activity.id }
                                        setActivities(newActivities.toMutableList())
                                        ActivityList.remove(activity)
                                        println("After Delete: $ActivityList")
                                    }
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) { IntroScreen() }

    //val act = StringBuffer("{\"resource_state\":3,\"athlete\":{\"id\":116468534,\"resource_state\":1},\"name\":\"TestWalk1\",\"distance\":9.7,\"moving_time\":28,\"elapsed_time\":28,\"total_elevation_gain\":0.0,\"type\":\"Walk\",\"sport_type\":\"Walk\",\"id\":8906239232,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"timezone\":\"(GMT+01:00) Europe/Ljubljana\",\"utc_offset\":7200.0,\"location_city\":null,\"location_state\":null,\"location_country\":null,\"achievement_count\":0,\"kudos_count\":0,\"comment_count\":0,\"athlete_count\":1,\"photo_count\":0,\"map\":{\"id\":\"a8906239232\",\"polyline\":\"}md{Germ~A@CDJG@?A\",\"resource_state\":3,\"summary_polyline\":\"}md{Germ~A@CDJG@?A\"},\"trainer\":false,\"commute\":false,\"manual\":false,\"private\":false,\"visibility\":\"everyone\",\"flagged\":false,\"gear_id\":null,\"start_latlng\":[46.55855353921652,15.639551980420947],\"end_latlng\":[46.55855278484523,15.639512753114104],\"average_speed\":0.346,\"max_speed\":1.143,\"average_cadence\":53.0,\"has_heartrate\":false,\"heartrate_opt_out\":false,\"display_hide_heartrate_option\":false,\"elev_high\":270.7,\"elev_low\":270.6,\"upload_id\":9556445684,\"upload_id_str\":\"9556445684\",\"external_id\":\"379c3765-799a-4e89-9e2c-94ca96c1de9f-activity.fit\",\"from_accepted_tag\":false,\"pr_count\":0,\"total_photo_count\":0,\"has_kudoed\":false,\"description\":null,\"calories\":0,\"perceived_exertion\":null,\"prefer_perceived_exertion\":false,\"segment_efforts\":[],\"splits_metric\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"splits_standard\":[{\"distance\":9.7,\"elapsed_time\":28,\"elevation_difference\":0.1,\"moving_time\":28,\"split\":1,\"average_speed\":0.35,\"average_grade_adjusted_speed\":null,\"pace_zone\":0}],\"laps\":[{\"id\":30263608009,\"resource_state\":2,\"name\":\"Lap 1\",\"activity\":{\"id\":8906239232,\"resource_state\":1},\"athlete\":{\"id\":116468534,\"resource_state\":1},\"elapsed_time\":33,\"moving_time\":33,\"start_date\":\"2023-04-17T11:47:46Z\",\"start_date_local\":\"2023-04-17T13:47:46Z\",\"distance\":9.69,\"start_index\":0,\"end_index\":14,\"total_elevation_gain\":0.0,\"average_speed\":0.29,\"max_speed\":1.143,\"average_cadence\":53.0,\"device_watts\":false,\"lap_index\":1,\"split\":1}],\"photos\":{\"primary\":null,\"count\":0},\"stats_visibility\":[{\"type\":\"heart_rate\",\"visibility\":\"everyone\"},{\"type\":\"pace\",\"visibility\":\"everyone\"},{\"type\":\"power\",\"visibility\":\"everyone\"},{\"type\":\"speed\",\"visibility\":\"everyone\"},{\"type\":\"calories\",\"visibility\":\"everyone\"}],\"hide_from_home\":false,\"device_name\":\"Strava Android App\",\"embed_token\":\"6de18481da7f53c27ac4029fd375a37fb027a807\",\"available_zones\":[]}")
    //val stream = StringBuffer("{\"moving\":{\"data\":[false,false,false,false,false,false,true,true,true,true,true,true,false,false,false],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"latlng\":{\"data\":[[46.558554,15.639552],[46.558555,15.639549],[46.558553,15.639556],[46.55855,15.639562],[46.558547,15.63957],[46.558548,15.639559],[46.558515,15.639518],[46.558526,15.639511],[46.558534,15.63951],[46.558542,15.639509],[46.558549,15.639507],[46.558557,15.639506],[46.558558,15.639502],[46.558562,15.639512],[46.558553,15.639513]],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"velocity_smooth\":{\"data\":[0.0,0.0,0.0,0.0,0.0,0.0,0.994,0.845,1.143,0.566,0.472,0.566,0.378,0.315,0.189],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"grade_smooth\":{\"data\":[1.7,1.5,1.3,1.1,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"cadence\":{\"data\":[0,0,62,62,53,53,53,53,53,0,0,0,0,0,0],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"distance\":{\"data\":[0.0,0.0,0.0,0.0,0.0,0.0,5.0,5.9,6.9,7.8,8.7,9.7,9.7,9.7,9.7],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"altitude\":{\"data\":[270.6,270.6,270.6,270.6,270.6,270.6,270.7,270.7,270.7,270.7,270.7,270.7,270.7,270.7,270.7],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"},\"time\":{\"data\":[0,5,6,7,10,13,15,17,19,20,23,24,25,26,28],\"series_type\":\"distance\",\"original_size\":15,\"resolution\":\"high\"}}")

    /*
    val jsonObject = JSONObject(act.toString())

    ActivityList.add(Activity(null, null, null, false, jsonObject))

    var name = jsonObject.getString("name")
    println(name)
    jsonObject.put("name", "editedName")
    name = jsonObject.getString("name")
    println(name)
    */

    //sendData("user", act, stream)
}