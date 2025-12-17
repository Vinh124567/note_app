package com.example.note_app.presentation.note_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.note_app.R
import com.example.note_app.domain.model.Note
import com.example.note_app.presentation.note_list.components.DashBoardGridView
import com.example.note_app.ui.theme.color836CFB
import com.example.note_app.ui.theme.colorB3A4FF
import com.example.note_app.ui.theme.colorE7E3FD

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel,
    onNavigateToAddEditNote: (Int?) -> Unit
) {
    val state = viewModel.state.value

    NoteListContent(
        state = state,
        onNavigateToAddEditNote = onNavigateToAddEditNote,
        onDeleteNote = { note ->
            viewModel.onEvent(NoteListEvent.DeleteNote(note))
        },
        onAddNoteClick = { onNavigateToAddEditNote(null) }
    )
}

@Composable
fun NoteListContent(
    state: NoteListState,
    onNavigateToAddEditNote: (Int?) -> Unit,
    onDeleteNote: (Note) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm ghi chú"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Chào Ngọt nhé !",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Ứng dụng ghi Chú",
                            fontSize = 30.sp,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_notify),
                        contentDescription = "Thông báo",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = color836CFB,
                                shape = RoundedCornerShape(23.dp)
                            )
                            .padding(horizontal = 34.dp, vertical = 54.dp)
                    ) {
                        Text("Hello")
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                            .background(
                                color = colorB3A4FF,
                                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            )
                            .padding(horizontal = 34.dp, vertical = 11.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 45.dp)
                            .background(
                                color = colorE7E3FD,
                                shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp)
                            )
                            .padding(horizontal = 34.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                DashBoardGridView(
                    onPersonalClick = { },
                    onWorkClick = { },
                    onPrivateClick = { },
                    onOthersClick = { }
                )
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun NoteListContentPreview() {
    MaterialTheme {
        NoteListContent(
            state = NoteListState(
                notes = listOf(
                    Note(
                        id = 1,
                        title = "Ghi chú mẫu 1",
                        content = "Đây là nội dung ghi chú mẫu để preview giao diện",
                        timestamp = System.currentTimeMillis(),
                        color = 0xFFFFAB91.toInt()
                    ),
                    Note(
                        id = 2,
                        title = "Ghi chú mẫu 2",
                        content = "Thêm một ghi chú nữa để xem LazyColumn hoạt động",
                        timestamp = System.currentTimeMillis(),
                        color = 0xFF81C784.toInt()
                    )
                )
            ),
            onNavigateToAddEditNote = {},
            onDeleteNote = {},
            onAddNoteClick = {}
        )
    }
}