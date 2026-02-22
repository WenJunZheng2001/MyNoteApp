package com.example.mynoteapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mynoteapp.commons.DetailScreenType
import com.example.mynoteapp.routes.DetailScreenRoute
import com.example.mynoteapp.viewModels.NoteViewModel


@Composable
fun ListOfNotes(navController: NavController,  modifier: Modifier = Modifier, viewModel: NoteViewModel) {
    val listOfNotesState by viewModel.notes.collectAsState()
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(listOfNotesState){
            Card(
                border = BorderStroke(width = 1.dp,Color.Black ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
                    .clickable{
                        viewModel.initializeEditNote(it.id)
                        navController.navigate(DetailScreenRoute( it.id, DetailScreenType.EDIT_NOTE))
                    }) {
                Row() {
                    Text(it.title, modifier = Modifier.padding(10.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        navController.navigate(DetailScreenRoute(it.id, DetailScreenType.EDIT_NOTE))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",)
                    }
                    IconButton(onClick = {
                        viewModel.deleteNote(it.id)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",)
                    }
                }

            }
        }
    }
}
