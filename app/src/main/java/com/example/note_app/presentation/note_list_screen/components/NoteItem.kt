package com.example.note_app.presentation.note_list_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.note_app.R
import com.example.note_app.data.local.entity.NoteType
import com.example.note_app.domain.model.Note

@Composable
fun FlexibleCardLayout(
    notes: List<Note>,
    onAddNoteClick: () -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        item {
            AddNoteButton(onClick = onAddNoteClick)
        }
        items(notes) { note ->
            NoteItem(note = note)
        }
    }
}

@Composable
fun AddNoteButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add_new_note),
                contentDescription = "Image"
            )
            Text(text = "+ Thêm note mới")
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = note.title)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content)
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 400,
    heightDp = 800
)
@Preview(
    showBackground = true,
    widthDp = 400,
    heightDp = 800
)
@Composable
fun FlexibleCardLayoutPreview() {
    FlexibleCardLayout(
        onAddNoteClick = {},
        notes = listOf(
            Note(
                title = "Note 1",
                content = "Nội dung ngắn",
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[0],
                noteType = NoteType.STUDY
            ),
            Note(
                title = "Note 2",
                content = "Nội dung dài hơn để test staggered grid layout trong Jetpack Compose",
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[1],
                noteType = NoteType.STUDY
            ),
            Note(
                title = "Note 3",
                content = "Short",
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[2],
                noteType = NoteType.STUDY
            ),
            Note(
                title = "Note 4",
                content = "Rất rất rất rất dài để thấy rõ các card có chiều cao khác nhau trong staggered grid",
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[3],
                noteType = NoteType.STUDY
            )
        )
    )
}

