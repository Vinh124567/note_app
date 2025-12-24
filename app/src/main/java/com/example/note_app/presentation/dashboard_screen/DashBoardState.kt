package com.example.note_app.presentation.dashboard_screen

import com.example.note_app.domain.model.Note

data class ActionListState(
    val notes: List<Note> = emptyList()
)

sealed class ActionListEvent {
    data class DeleteAction(val note: Note) : ActionListEvent()
    object RestoreAction : ActionListEvent()
}