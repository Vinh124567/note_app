package com.example.note_app.presentation.note_list

import com.example.note_app.domain.model.Note

data class NoteListState(
    val notes: List<Note> = emptyList()
)

sealed class NoteListEvent {
    data class DeleteNote(val note: Note) : NoteListEvent()
    object RestoreNote : NoteListEvent()
}