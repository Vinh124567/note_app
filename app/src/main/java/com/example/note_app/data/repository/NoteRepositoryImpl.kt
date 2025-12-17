package com.example.note_app.data.repository

import com.example.note_app.data.local.dao.NoteDao
import com.example.note_app.data.local.entity.toEntity
import com.example.note_app.domain.model.Note
import com.example.note_app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getAllNote(): Flow<List<Note>> =
        dao.getAllNote().map { list ->
            list.map { it.toNote() }
        }

    override suspend fun getNoteById(id: Int): Note? =
        dao.getNoteById(id)?.toNote()

    override suspend fun insertNote(note: Note) =
        dao.insertNote(note.toEntity())

    override suspend fun deleteNote(note: Note) {
        note.id?.let { dao.deleteNoteById(it) }
    }
}
