package data.data.model

import java.time.LocalDateTime

data class Activity(
    val id: String,
    var name: String,
    val distance: Double,
    var type: String,
    val date: LocalDateTime
)
/*
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
                val newActivities = activities.filter { it.id != activity.id }
                setActivities(newActivities.toMutableList())

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
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = editedName,
                                onValueChange = { editedName = it },
                                label = { Text("Name") }
                            )
                            TextField(
                                value = editedType,
                                onValueChange = { editedType = it },
                                label = { Text("Type") }
                            )
                            IconButton(
                                onClick = {
                                    activity.name = editedName
                                    activity.type = editedType
                                    isEditing = false
                                    println("After:  $ActivityList")
                                }
                            ) {
                                Icon(Icons.Filled.Check, contentDescription = "Save")
                            }
                            IconButton(
                                onClick = {
                                    activity.name = activity.name
                                    activity.type = activity.type
                                    isEditing = false
                                    println("After:  $ActivityList")
                                }
                            ) {
                                Icon(Icons.Filled.Close, contentDescription = "Cancel")
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
                            }
                            Row {
                                IconButton(
                                    onClick = { isEditing = true }
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                                }
                                IconButton(
                                    onClick = {
                                        val newActivities = activities.filter { it.id != activity.id }
                                        setActivities(newActivities.toMutableList())
                                        ActivityList.remove(activity)
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
}*/
