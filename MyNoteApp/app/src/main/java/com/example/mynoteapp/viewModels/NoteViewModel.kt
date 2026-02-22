package com.example.mynoteapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.commons.DetailScreenType
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

    private val _editNoteState = MutableStateFlow(NoteData(-1, "", ""))
    val editNoteState: StateFlow<NoteData> = _editNoteState.asStateFlow()
    val notes: StateFlow<List<NoteData>> =
        noteRepository.getAll()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    fun updateLocalNote(actionType: DetailScreenType, title: String? = null, description: String? = null){
        when(actionType){
            DetailScreenType.CREATE_NOTE -> {
                _newNoteState.update {
                    it.copy(
                        title = title ?: it.title,
                        description = description ?: it.description)
                }
            }
            DetailScreenType.EDIT_NOTE -> {
                _editNoteState.update {
                    it.copy(
                        title = title ?: it.title,
                        description = description ?: it.description)
                }
            }
        }
    }

    fun initializeEditNote(id: Int){
        val note = notes.value.find { it.id == id }
        println(note?.title)
        if(note != null) {
            _editNoteState.update {
                it.copy(
                    id = note.id,
                    title = note.title,
                    description = note.description
                )
            }
        }
    }

    fun deleteNote(id: Int): Boolean{
        val note = notes.value.find { it.id == id }
        if(note == null){
            return false
        }else{
            viewModelScope.launch {
                noteRepository.delete(note)
            }
            return true
        }
    }

    fun editNote(): Boolean{
        return true;
    }

    fun createNote(): Boolean{
        try {
            if(newNoteState.value.title.isNullOrEmpty()){
                return false
            }
            val scope = viewModelScope.launch {
                noteRepository.insert(NoteEntity(
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