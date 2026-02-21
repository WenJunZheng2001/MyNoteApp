package com.example.mynoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mynoteapp.models.NoteData
import com.example.mynoteapp.routes.DetailScreenRoute
import com.example.mynoteapp.routes.HomeScreenRoute
import com.example.mynoteapp.ui.theme.MyNoteAppTheme
import com.example.mynoteapp.viewModels.NoteViewModel

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
                detailScreenParams.appBarTitle,
                detailScreenParams.noteId,
                viewModel
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: NoteViewModel){
    Scaffold(
        topBar = {
            TopAppBar(
                title= {Text("Block Note", fontWeight = FontWeight.SemiBold)},
                actions = {
                    IconButton(onClick = {
                        navController.navigate(DetailScreenRoute(null,"New Note"))
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add new note")
                    } }
            )},
        containerColor = Color.LightGray
    ) { innerPadding ->
        ListOfNotes(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}


@Composable
fun ListOfNotes(navController: NavController,  modifier: Modifier = Modifier, viewModel: NoteViewModel) {
    val listOfNotesState by viewModel.listOfNotesState.collectAsState()
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(listOfNotesState){
            Card(
                border = BorderStroke(width = 1.dp,Color.Black ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
                    .clickable{
                        navController.navigate(DetailScreenRoute( it.id,"Edit Note"))
                    }) {
                Row() {
                    Text(it.title, modifier = Modifier.padding(10.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",)
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",)
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, appBarTitle: String, id: Int?, viewModel: NoteViewModel) {
    val noteInfo: NoteData? = if(id != null) viewModel.getModelById(id) else
        viewModel.newNoteState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title= {Text(appBarTitle, fontWeight = FontWeight.SemiBold)},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save note")
                    } }
            )},
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)){
            item {
                TextField(
                    value = noteInfo?.title ?: "",
                    label = {Text("Title")},
                    onValueChange = {
                        viewModel.updateNewNote(title = it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item{
                TextField(
                    value = noteInfo?.description ?: "",
                    label = {Text("Description")},
                    onValueChange = {
                        viewModel.updateNewNote(description = it)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}