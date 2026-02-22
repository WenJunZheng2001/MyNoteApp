package com.example.mynoteapp.services

import com.example.mynoteapp.models.NoteData
import com.example.mynoteapp.roomDatabase.NoteDatabase
import com.example.mynoteapp.roomDatabase.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepository @Inject constructor(
    val noteDatabase: NoteDatabase
) {
    fun getNotes(): Flow<List<NoteData>> {
        return noteDatabase
            .noteDao()
            .getAll()
            .map { list -> list.map { it.toData() } }
    }

    suspend fun insertNote(note: NoteEntity){
        noteDatabase.noteDao().insertAll(note)
    }

    fun NoteEntity.toData(): NoteData {
        return NoteData(
            id = uid,
            title = title,
            description = description
        )
    }

    fun NoteData.toEntity(): NoteEntity {
        return NoteEntity(
            uid = id,
            title = title,
            description = description
        )
    }
}