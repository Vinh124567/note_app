package com.example.note_app.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note_app.domain.model.Note
import com.example.note_app.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Int? = savedStateHandle.get<Int>("noteId")?.let {
        if (it == -1) null else it
    }

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle
    private val _noteColor = mutableStateOf(Color.Yellow.toArgb())
    val noteColor: State<Int> = _noteColor


    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    init {
        // ✅ Tự động load note nếu đang edit
        noteId?.let { id ->
            loadNote(id)
        }
    }

    private fun loadNote(id: Int) {
        viewModelScope.launch {
            repository.getNoteById(id)?.let { note ->
                _noteTitle.value = note.title
                _noteContent.value = note.content
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _noteTitle.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _noteContent.value = newContent
    }

    fun saveNote() {
        viewModelScope.launch {
            val note = Note(
                id = noteId,
                title = noteTitle.value,
                content = noteContent.value,
                timestamp = System.currentTimeMillis(),
                        color = noteColor.value
            )
            repository.insertNote(note)
        }
    }
}