package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationScreens()
                }
            }


        }
    }
}

@Composable
fun HomeScreen(navController: NavController){
    val appModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    val weightState: MutableState<String> = remember { mutableStateOf("") }
    val heightState: MutableState<String> = remember { mutableStateOf("") }

    Column (verticalArrangement = Arrangement.spacedBy(16.dp)){
        Text(stringResource(R.string.title_home), fontSize = 28.sp)

        OutlinedTextField(
            value = weightState.value,
            onValueChange = { weightState.value = it },
            label = { Text(stringResource(R.string.label_weight))},
            modifier= appModifier,

        )

        OutlinedTextField(
            value = heightState.value,
            onValueChange = { heightState.value = it },
            label = { Text(stringResource(R.string.label_height)) },
            modifier= appModifier,

        )

        Button(
            onClick = {
                val weight = weightState.value
                val height = heightState.value

                navController.navigate("main/${weight}/${height}")

            },
            modifier = appModifier,
            enabled = weightState.value.isNotBlank() && heightState.value.isNotBlank()
        ) {
            Text(stringResource(R.string.button_home))
        }
    }
}

@Composable
fun MainScreen(myViewModel: MyViewModel,navController: NavController, weight: String?, height: String?) {
    val appModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (weight != null && height != null) {
            val bmi = myViewModel.calculate(weight.toDouble(), height.toDouble())
            Text(
                text = stringResource(R.string.Result).format(bmi),

                )

            val bmiInfo = myViewModel.bmiInfo(bmi)
            Text(
                text = bmiInfo,

                )
        }

        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text(stringResource(R.string.Button_main))
        }
    }
}


@Composable
fun NavigationScreens(){
    val navController = rememberNavController()
    val myViewModel =MyViewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable(route="home"){
            HomeScreen(navController)
        }
        composable(route="main/{weight}/{height}",
            arguments = listOf(
                navArgument("weight"){
                    type= NavType.StringType
                },
                navArgument("height"){
                    type= NavType.StringType
                }
            )){
            var weight = it.arguments?.getString("weight")
            var height = it.arguments?.getString("height")
            MainScreen(myViewModel, navController, weight, height)
        }
}
}


@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    val navController = rememberNavController()
    MyApplicationTheme {
        HomeScreen(navController)
    }
}