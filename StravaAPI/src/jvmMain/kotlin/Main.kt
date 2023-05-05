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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.data.AuthenticationManager
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel


@Composable
fun IntroScreen() {
    var isBodyVisible by remember { mutableStateOf(false) }
    val (isButtonVisible, setIsButtonVisible) = remember { mutableStateOf(true) }

    Column(
        Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color.Cyan),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Header()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.LightGray)
        ) {
            if (isButtonVisible) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            isBodyVisible = true
                            setIsButtonVisible(false)
                        },
                        Modifier
                            .clip(RectangleShape),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color.Green),
                    ) {
                        Text(text = "Get Data")
                    }
                }
            }
            if (isBodyVisible) {
                GenerateData()
                Body()
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color.Cyan),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Footer()
        }
    }
}

fun GenerateData() {
    GetAccessToken()
    val activ = GetActivityIds()
    for (act in activ) {
        GetActivityList(act)
    }
}

@Composable
fun Header() {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Activities",
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
    }
}

@Composable
fun Footer() {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Parser",
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
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
                var isEditing by remember { mutableStateOf(false) }
                var editedName by remember { mutableStateOf(activity.name) }
                var editedType by remember { mutableStateOf(activity.type) }

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
                                        activity.name = editedName
                                        activity.type = editedType
                                        isEditing = false
                                        println("After Save:  $ActivityList")
                                    }
                                ) {
                                    Icon(Icons.Filled.Check, contentDescription = "Save")
                                }
                                IconButton(
                                    onClick = {
                                        activity.name = activity.name
                                        activity.type = activity.type
                                        isEditing = false
                                        println("After Cancel:  $ActivityList")
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
                            Column {
                                Text(
                                    text = activity.name,
                                    style = MaterialTheme.typography.h6
                                )
                                Text(
                                    text = activity.type,
                                    style = MaterialTheme.typography.subtitle2
                                )
                                Text(
                                    text = activity.date.toString(),
                                    style = MaterialTheme.typography.subtitle2
                                )
                                Text(
                                    text = "Razdalja: " + activity.distance.toString(),
                                    style = MaterialTheme.typography.h6
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
                                        println("After Delete:  $ActivityList")

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
    Window(onCloseRequest = ::exitApplication) {
        //WebScreen()
        IntroScreen()
    }
}