package com.example.mynoteapp.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mynoteapp.commons.DetailScreenType
import com.example.mynoteapp.models.NoteData
import com.example.mynoteapp.viewModels.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, id: Int?, viewModel: NoteViewModel, screenType: DetailScreenType) {
    val note: NoteData = if(id != null){
        viewModel.editNoteState.collectAsState().value
    }else{
        viewModel.newNoteState.collectAsState().value
    }
    println(note.title)
    val appTitle = if(screenType == DetailScreenType.CREATE_NOTE) "New Note" else "Edit Note"
    Scaffold(
        topBar = {
            TopAppBar(
                title= {Text(appTitle, fontWeight = FontWeight.SemiBold)},
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
                        when(screenType){
                            DetailScreenType.CREATE_NOTE -> {
                                val isSuccess = viewModel.createNote()
                                if(isSuccess) navController.navigateUp()
                            }
                            DetailScreenType.EDIT_NOTE -> {
                                val isSuccess = viewModel.editNote()
                            }
                        }

                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save note")
                    } }
            )},
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)){
            item {
                TextField(
                    value = note.title,
                    label = {Text("Title")},
                    onValueChange = {
                        when(screenType){
                            DetailScreenType.CREATE_NOTE ->{
                                viewModel.updateLocalNote(screenType, title = it)
                            }
                            DetailScreenType.EDIT_NOTE -> {
                                viewModel.updateLocalNote(screenType, title = it)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item{
                TextField(
                    value = note.description ?: "",
                    label = {Text("Description")},
                    onValueChange = {
                        when(screenType){
                            DetailScreenType.CREATE_NOTE ->{
                                viewModel.updateLocalNote(screenType, description = it)
                            }
                            DetailScreenType.EDIT_NOTE -> {
                                viewModel.updateLocalNote(screenType, description = it)
                                println("editing")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}