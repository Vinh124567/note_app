package com.example.note_app.presentation.note_list_screen

import com.example.note_app.domain.model.Note

data class NoteListState(
    val notes: List<Note> = emptyList()
)

sealed class NoteListEvent {
    data class DeleteAction(val note: Note) : NoteListEvent()
    object RestoreAction : NoteListEvent()
}