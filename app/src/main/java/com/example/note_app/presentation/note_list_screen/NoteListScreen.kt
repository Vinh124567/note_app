package com.example.note_app.presentation.note_list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.note_app.core.common.CommonAppBar
import com.example.note_app.presentation.navigation.Screen
import com.example.note_app.presentation.note_list_screen.components.FlexibleCardLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    viewModel: NoteListViewModel,
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            if (state.isSelectionMode) {
                TopAppBar(
                    title = { Text("Chế độ chọn") },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.exitSelectionMode() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Thoát chế độ chọn"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {

                            viewModel.deleteSelectedNotes() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Xóa ghi chú đã chọn"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            } else {
                CommonAppBar(
                    title = "Chi tiết ghi chú",
                    onBackClick = onBackClick,
                )
            }
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            // Search Bar
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            com.example.note_app.presentation.dashboard_screen.components.SearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) },
                onSearchActiveChange = { viewModel.onSearchActiveChange(it) },
                isActive = state.isSearchActive,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            
            FlexibleCardLayout(
                notes = state.notes,
                onAddNoteClick = { navController.navigate(Screen.AddEditNote.createRoute(null)) },
                nLongClick = { noteId -> viewModel.onNoteLongPress(noteId) },
                isSelectionMode = state.isSelectionMode,
                selectedNotes = state.selectedNotes,
                onNoteClick = { noteId ->
                    if (state.isSelectionMode) {
                        // 在选择模式下，选择/取消选择笔记
                        viewModel.onSelectNote(noteId)
                    } else {
                        // 在正常模式下，导航到编辑屏幕
                        navController.navigate(Screen.AddEditNote.createRoute(noteId))
                    }
                }
            )
        }
    }
}