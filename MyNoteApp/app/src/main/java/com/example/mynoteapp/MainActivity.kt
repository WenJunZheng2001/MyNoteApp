package com.example.mynoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mynoteapp.components.DetailScreen
import com.example.mynoteapp.components.HomeScreen
import com.example.mynoteapp.routes.DetailScreenRoute
import com.example.mynoteapp.routes.HomeScreenRoute
import com.example.mynoteapp.ui.theme.MyNoteAppTheme
import com.example.mynoteapp.viewModels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyNoteAppTheme {
                AppNavComposable()
            }
        }
    }
}
@Composable
fun AppNavComposable(viewModel: NoteViewModel = viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreenRoute) {
        composable<HomeScreenRoute> {
            HomeScreen(navController, viewModel)
        }
        composable<DetailScreenRoute> { backStackEntry ->
            val detailScreenParams: DetailScreenRoute = backStackEntry.toRoute()
            DetailScreen(
                navController,
                detailScreenParams.noteId,
                viewModel,
                detailScreenParams.screenType
            )
        }
    }
}
