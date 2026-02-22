package com.example.mynoteapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mynoteapp.commons.DetailScreenType
import com.example.mynoteapp.routes.DetailScreenRoute
import com.example.mynoteapp.viewModels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: NoteViewModel){
    Scaffold(
        topBar = {
            TopAppBar(
                title= {Text("Block Note", fontWeight = FontWeight.SemiBold)},
                actions = {
                    IconButton(onClick = {
                        navController.navigate(DetailScreenRoute(null, DetailScreenType.CREATE_NOTE))
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