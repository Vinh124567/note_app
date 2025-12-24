package com.example.note_app.presentation.navigation

import com.example.note_app.data.local.entity.NoteType

sealed class Screen(val route: String) {

    object DashBoard : Screen("dashboard")

    object AddEditNote : Screen("add_edit_note/{noteId}") {
        fun createRoute(noteId: Int? = null): String =
            "add_edit_note/${noteId ?: -1}"
    }

    object Onboarding : Screen("onboarding")

    object NoteList : Screen("note_list/{noteType}") {
        fun createRoute(noteType: NoteType): String =
            "note_list/${noteType.name}"
    }
}
