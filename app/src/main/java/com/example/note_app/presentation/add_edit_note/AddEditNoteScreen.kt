package com.example.note_app.presentation.add_edit_note

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel,
    onNavigateBack: () -> Unit
) {
    val title = viewModel.noteTitle.value
    val content = viewModel.noteContent.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { viewModel.onTitleChange(it) },
            placeholder = { Text("Tiêu đề") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = content,
            onValueChange = { viewModel.onContentChange(it) },
            placeholder = { Text("Nội dung") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveNote()
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lưu")
        }
    }
}