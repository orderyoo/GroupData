package com.example.groupdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.groupdata.ui.theme.GroupDataTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dependencies.init(applicationContext)
        val viewModel = MainViewModel(Dependencies.repository)
        viewModel.getListSchoolDataFromLocalDB()

        setContent {
            var list by remember { mutableStateOf(viewModel.tempLiveData.value) }
            viewModel.tempLiveData.observe(this){
                list = it
            }
            GroupDataTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        ListOfItem(list, viewModel, navController)
                    }
                    composable("open") {
                        OpenList(viewModel, navController, this@MainActivity)
                    }
                }
            }
        }
    }
}

