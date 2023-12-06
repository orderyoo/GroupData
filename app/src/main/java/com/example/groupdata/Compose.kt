
package com.example.groupdata

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfItem(
    list: MutableList<SchoolData>?,
    viewModel: MainViewModel,
    navController: NavController
){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ){
        Scaffold(
            modifier = Modifier.weight(1f),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.createNewGroup{
                            navController.navigate("open")
                        }
                    },
                    content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") }
                )
            },
            floatingActionButtonPosition = FabPosition.End
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(list.orEmpty()) {index, item->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 75.dp)
                            .padding(5.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                        onClick = {
                            viewModel.setCurrentItem(item)
                            navController.navigate("open")
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ){
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentWidth()
                                    .padding(5.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(text = "Group: " + item.group.groupName,
                                    fontSize = 32.sp)
                            }

                            Box(
                                Modifier.fillMaxWidth(),
                            ){
                                Button(
                                    modifier = Modifier
                                        .size(75.dp)
                                        .align(Alignment.CenterEnd),
                                    onClick = {
                                        viewModel.deleteGroup(item, index)
                                        navController.navigate("main")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Black
                                    ),
                                    content = { Icon(Icons.Filled.Delete, contentDescription = "Удалить") },
                                    shape = RoundedCornerShape(0.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ){
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    modifier = Modifier
                        .height(75.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 10.dp
                    ),
                    content = {Icon(Icons.Filled.PushPin, contentDescription = "ЗАПУШИТЬ", modifier = Modifier.size(75.dp) )},
                    onClick = {
                        viewModel.Push()
                    }
                )

                Button(
                    modifier = Modifier
                        .height(75.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 10.dp
                    ),
                    content = {Icon(Icons.Filled.Download, contentDescription = "ЗАГРУЗИТЬ", modifier = Modifier.size(75.dp) )},
                    onClick = {
                        viewModel.getListSchoolDataFromServer()

                    }
                )
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenList(
    viewModel: MainViewModel,
    navController: NavController,
    context: MainActivity
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.createNewStudent()
                },
                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить") }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(5.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ){
                    Box(Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Column {
                            var textGroup by remember { mutableStateOf(viewModel.currentGroup.value!!.group.groupName) }
                            OutlinedTextField(
                                value = textGroup,
                                onValueChange = {
                                    textGroup = it
                                    viewModel.setNameGroup(textGroup)
                                },
                                modifier = Modifier
                                    .padding(2.dp),
                                placeholder = { Text(text = "Name Group") },
                                shape = RoundedCornerShape(15.dp),
                                maxLines = 20,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.background
                                )
                            )
                            Text(text = "  number of students: " + viewModel.currentGroup.value!!.students.size,
                                fontSize = 14.sp)
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 10.dp
                    ),
                    content = {Icon(Icons.Filled.Save, contentDescription = "Сохранить ГРУППУ", modifier = Modifier.size(75.dp) )},
                    onClick = {
                        viewModel.saveAll()
                        navController.navigate("main")
                    }
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            var thisItem by remember { mutableStateOf(viewModel.currentGroup.value!!.students) }
            viewModel.currentGroup.observe(context){
                thisItem = it.students

            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(thisItem.orEmpty()) { index, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 100.dp)
                            .padding(5.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    ) {
                        Column {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                            ){
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(2.dp)
                                        .weight(1f),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                                ){
                                    Box(Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Column {
                                            Text(text = "Student #" + (index+1))
                                        }
                                    }
                                }
                                Button(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(2.dp),
                                    shape = RoundedCornerShape(12.5.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 10.dp
                                    ),
                                    content = {Icon(Icons.Filled.Delete, contentDescription = "Удалить студента" )},
                                    onClick = {
                                        viewModel.deleteStudent(index)
                                        navController.navigate("open")
                                    }
                                )
                            }
                            Row(
                            ){
                                Box(modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = "Name: ",
                                        fontSize = 24.sp)
                                }
                                var text by remember { mutableStateOf(viewModel.currentGroup.value!!.students.get(index).name) }
                                OutlinedTextField(
                                    value = text,
                                    onValueChange = {
                                        text = it
                                        viewModel.setNameStudent(text, index)
                                    },
                                    modifier = Modifier
                                        .padding(2.dp),
                                    placeholder = { Text(text = "Name") },
                                    shape = RoundedCornerShape(15.dp),
                                    maxLines = 20,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        unfocusedBorderColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                            Row(){
                                Box(modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = "Age: ",
                                        fontSize = 24.sp)
                                }
                                var text1 by remember { mutableStateOf(viewModel.currentGroup.value!!.students.get(index).age) }
                                OutlinedTextField(
                                    value = text1,
                                    onValueChange = {
                                        text1 = it
                                        viewModel.setAgeStudent(text1, index)
                                    },
                                    modifier = Modifier
                                        .padding(2.dp),
                                    placeholder = { Text(text = "Age") },
                                    shape = RoundedCornerShape(15.dp),
                                    maxLines = 3,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        unfocusedBorderColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                            Row(){
                                Box(modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = "Grade: ",
                                        fontSize = 24.sp)
                                }
                                var text2 by remember { mutableStateOf(viewModel.currentGroup.value!!.students.get(index).grade) }
                                OutlinedTextField(
                                    value = text2,
                                    onValueChange = {
                                        text2 = it
                                        viewModel.setGradeStudent(text2, index)
                                    },
                                    modifier = Modifier
                                        .padding(2.dp),
                                    placeholder = { Text(text = "Grade") },
                                    shape = RoundedCornerShape(15.dp),
                                    maxLines = 2,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        unfocusedBorderColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


