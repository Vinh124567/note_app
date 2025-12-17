package com.example.note_app.presentation.navigation

sealed class Screen(val route: String) {
    object NoteList : Screen("note_list")
    object AddEditNote : Screen("add_edit_note/{noteId}") {
        fun createRoute(noteId: Int? = null) = if (noteId != null) {
            "add_edit_note/$noteId"
        } else {
            "add_edit_note/-1"
        }
    }

    object Onboarding : Screen("onboarding")

}