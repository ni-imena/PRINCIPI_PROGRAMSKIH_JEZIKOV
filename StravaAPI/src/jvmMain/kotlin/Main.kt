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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.data.model.Weather
import data.data.sendData
import data.data.sendWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var ActivitiesGenerated: Boolean = false
var WeatherGenerated: Boolean = false

fun Generate() {
    ActivityList.clear()
    GetAccessToken()
    val activ = GetActivityIds()
    for (act in activ) {
        GetActivityList(act)
    }
    ActivitiesGenerated = true
}

fun Save() {
    for (item in ActivityList) {
        if (item.select) {
            sendData(item.stravaUserId, item.json, GetActivityStream(item.json.getLong("id").toString()))
        }
    }
}

fun SaveWeather() {
    for (item in WeatherList) {
        sendWeatherData(item)
    }
}

fun GenerateWeather() {
    WeatherList.clear()
    getWeather()
    for (item in WeatherList) {
        println(String.format("%-40s%-25s%-25s%-25s", item.city, item.temperature, item.aproxCloud, item.humidity))
    }
    WeatherGenerated = true
}

enum class Tab {
    Activities, Weather
}

@Composable
fun ActivitiesTab() {
    val (hasActivities, setHasActivities) = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ActivitiesGenerated) {
            Activities()
        } else {
            Text(text = "No Activities yet Generated")
            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000)
                    if (ActivitiesGenerated) {
                        setHasActivities(ActivitiesGenerated)
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherTab() {
    val (hasWeather, setHasWeather) = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (WeatherGenerated) {
            Weather()
        } else {
            Text(text = "No Weather yet Generated")
            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000)
                    if (WeatherGenerated) {
                        setHasWeather(WeatherGenerated)
                    }
                }
            }
        }
    }
}

@Composable
fun Weather() {
    val (weatherList, setWeatherList) = remember { mutableStateOf(WeatherList) }
    val key = remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "City",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.3f)
            )
            Text(
                text = "Temperature",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = "Aprox Cloudiness",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.3f)
            )
            Text(
                text = "Humidity",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.2f)
            )
            Row {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }
        key(key.value) {
            LazyColumn {
                items(weatherList) { weather ->
                    val city = weather.city
                    val temperature = weather.temperature
                    val aproxCloud = weather.aproxCloud
                    val humidity = weather.humidity

                    var isEditing by remember { mutableStateOf(false) }
                    var editedCity by remember { mutableStateOf(city) }
                    var editedTemperature by remember { mutableStateOf(temperature) }
                    var editedAproxCloud by remember { mutableStateOf(aproxCloud) }
                    var editedHumidity by remember { mutableStateOf(humidity) }


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
                                    modifier = Modifier.weight(1f),
                                    value = editedCity,
                                    onValueChange = { editedCity = it },
                                    label = { Text("City") }
                                )
                                TextField(
                                    modifier = Modifier.weight(1f),
                                    value = editedTemperature,
                                    onValueChange = { editedTemperature = it },
                                    label = { Text("Temperature") }
                                )
                                TextField(
                                    modifier = Modifier.weight(1f),
                                    value = editedAproxCloud,
                                    onValueChange = { editedAproxCloud = it },
                                    label = { Text("Approximate Cloud") }
                                )
                                TextField(
                                    modifier = Modifier.weight(1f),
                                    value = editedHumidity,
                                    onValueChange = { editedHumidity = it },
                                    label = { Text("Humidity") }
                                )
                                Row {
                                    IconButton(
                                        onClick = {
                                            weather.city = editedCity
                                            weather.temperature = editedTemperature
                                            weather.aproxCloud = editedAproxCloud
                                            weather.humidity = editedHumidity
                                            isEditing = false
                                            println("After Save: $WeatherList")
                                        }
                                    ) {
                                        Icon(Icons.Filled.Check, contentDescription = "Save")
                                    }
                                    IconButton(
                                        onClick = {
                                            isEditing = false
                                            println("After Cancel: $weatherList")
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = city,
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.weight(0.3f)
                                )
                                Text(
                                    text = temperature,
                                    style = MaterialTheme.typography.subtitle2,
                                    modifier = Modifier.weight(0.2f)
                                )
                                Text(
                                    text = aproxCloud,
                                    style = MaterialTheme.typography.subtitle2,
                                    modifier = Modifier.weight(0.3f)
                                )
                                Text(
                                    text = humidity,
                                    style = MaterialTheme.typography.subtitle2,
                                    modifier = Modifier.weight(0.2f)
                                )
                                Row {
                                    IconButton(
                                        onClick = { isEditing = true }
                                    ) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                                    }
                                    IconButton(
                                        onClick = {
                                            println("Before Delete: $WeatherList")
                                            WeatherList.remove(weather)
                                            setWeatherList(WeatherList)
                                            key.value += 1
                                            println("After Delete: $WeatherList")
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
}

@Composable
fun Intro() {
    var selectedTab by remember { mutableStateOf(Tab.Activities) }
    var isActivityGenerated by remember { mutableStateOf(false) }
    var isWeatherGenerated by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = if (selectedTab == Tab.Activities) 0 else 1,
                modifier = Modifier.height(50.dp)
            ) {
                Tab(
                    selected = selectedTab == Tab.Activities,
                    onClick = { selectedTab = Tab.Activities }
                ) {
                    Text(text = "Activities")
                }
                Tab(
                    selected = selectedTab == Tab.Weather,
                    onClick = { selectedTab = Tab.Weather }
                ) {
                    Text(text = "Weather")
                }
            }
        },
        content = {
            when (selectedTab) {
                Tab.Activities -> ActivitiesTab()
                Tab.Weather -> WeatherTab()
            }
        },
        bottomBar = {
            if (selectedTab == Tab.Activities) {
                if (isActivityGenerated) {
                    Button(
                        onClick = { Save() },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Save")
                    }
                } else {
                    Button(
                        onClick = {
                            Generate()
                            isActivityGenerated = true
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Generate")
                    }
                }
            }
            if (selectedTab == Tab.Weather) {
                if (isWeatherGenerated) {
                    Button(
                        onClick = { SaveWeather() },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Save(${WeatherList.size})")
                    }
                } else {
                    Button(
                        onClick = {
                            GenerateWeather()
                            isWeatherGenerated = true
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Generate")
                    }
                }
            }
        }
    )
}

@Composable
fun Activities() {
    val (activities, setActivities) = remember { mutableStateOf(ActivityList) }
    val key = remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        key(key.value) {
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
                                        .width(55.dp)
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
                                            ActivityList.remove(activity)
                                            setActivities(ActivityList)
                                            key.value += 1
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
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) { Intro() }
}
