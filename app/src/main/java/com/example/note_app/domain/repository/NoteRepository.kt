package com.example.note_app.domain.repository

import com.example.note_app.data.local.entity.NoteType
import com.example.note_app.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNote(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getNoteByType(type: NoteType): Flow<List<Note>>

    fun searchNotes(query: String): Flow<List<Note>>

    fun searchNotesByType(type: NoteType, query: String): Flow<List<Note>>
}