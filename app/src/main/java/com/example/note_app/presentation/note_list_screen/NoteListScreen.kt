package com.example.note_app.presentation.note_list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.note_app.core.common.CommonAppBar
import com.example.note_app.data.local.entity.NoteType
import com.example.note_app.presentation.add_edit_note.AddEditNoteViewModel
import com.example.note_app.presentation.navigation.Screen
import com.example.note_app.presentation.note_list_screen.components.FlexibleCardLayout

@Composable
fun NoteListScreen(
    navController: NavController,

    onBackClick: () -> Unit, viewModel: NoteListViewModel,
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "Chi tiết ghi chú",
                onBackClick = onBackClick,
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            FlexibleCardLayout(viewModel.state.value.notes, onAddNoteClick = {
                navController.navigate(Screen.AddEditNote.createRoute(null))
            })
        }
    }
}
