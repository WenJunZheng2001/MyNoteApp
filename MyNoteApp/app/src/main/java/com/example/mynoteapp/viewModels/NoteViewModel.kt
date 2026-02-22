package com.example.mynoteapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.models.NoteData
import com.example.mynoteapp.roomDatabase.NoteEntity
import com.example.mynoteapp.services.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(val noteRepository: NoteRepository) : ViewModel() {
    private val _newNoteState = MutableStateFlow(NoteData(-1, "", ""))
    val newNoteState: StateFlow<NoteData> = _newNoteState.asStateFlow()
    val notes: StateFlow<List<NoteData>> =
        noteRepository.getNotes()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    fun updateNewNote(title: String? = null, description: String? = null){
        _newNoteState.update {
            it.copy(
                title = title ?: it.title,
                description = description ?: it.description)
        }
    }

    fun createNote(): Boolean{
        try {
            val scope = viewModelScope.launch {
                noteRepository.insertNote(NoteEntity(
                    title = newNoteState.value.title,
                    description =  newNoteState.value.description))
            }
            _newNoteState.update {
                it.copy(
                    title = "",
                    description = ""
                )
            }
            return true
        }catch (e: Exception){
            return false
        }

    }

    fun getModelById(id:Int): NoteData?{
        return notes.value.find { it.id == id  }
    }
}