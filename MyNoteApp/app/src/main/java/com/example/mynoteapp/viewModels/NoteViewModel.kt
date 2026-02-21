package com.example.mynoteapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.mynoteapp.models.NoteData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class NoteViewModel : ViewModel() {
    private val _newNoteState = MutableStateFlow(NoteData(-1, "", ""))
    val newNoteState: StateFlow<NoteData> = _newNoteState.asStateFlow()
    private val _listOfNotes = MutableStateFlow(listOf<NoteData>())
    val listOfNotesState: StateFlow<List<NoteData>> = _listOfNotes.asStateFlow()
//    val notes: StateFlow<List<NoteInfo>> = repository.getAllNotes()
//    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
//
//    init{
//        val scope = viewModelScope.launch {
//            repository.addNewNote("hi", "pizza")
//        }
//
//    }

    fun updateNewNote(title: String? = null, description: String? = null){
        _newNoteState.update {
            it.copy(
                title = title ?: it.title,
                description = description ?: it.description)
        }
    }
    fun getModelById(id:Int): NoteData?{
        return _listOfNotes.value.find { it.id == id  }
    }
}