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
import com.example.note_app.data.local.entity.NoteType

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

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    private val _noteColor = mutableStateOf(0xFFBAE1FF.toInt())
    val noteColor: State<Int> = _noteColor

    private val _noteType = mutableStateOf(NoteType.PERSONAL)
    val noteType: State<NoteType> = _noteType

    private val _showTypeSelector = mutableStateOf(false)
    val showTypeSelector: State<Boolean> = _showTypeSelector

    private val _showColorPicker = mutableStateOf(false)
    val showColorPicker: State<Boolean> = _showColorPicker

    init {
        noteId?.let { id ->
            loadNote(id)
        }
    }

    private fun loadNote(id: Int) {
        viewModelScope.launch {
            repository.getNoteById(id)?.let { note ->
                _noteTitle.value = note.title
                _noteContent.value = note.content
                _noteColor.value = note.color
                _noteType.value = note.noteType
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _noteTitle.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _noteContent.value = newContent
    }

    fun onColorChange(newColor: Int) {
        _noteColor.value = newColor
    }

    fun onTypeChange(newType: NoteType) {
        _noteType.value = newType
    }

    fun showTypeSelector() {
        _showTypeSelector.value = true
    }

    fun hideTypeSelector() {
        _showTypeSelector.value = false
    }

    fun showColorPicker() {
        _showColorPicker.value = true
    }

    fun hideColorPicker() {
        _showColorPicker.value = false
    }

    fun saveNote() {
        viewModelScope.launch {
            val note = Note(
                id = noteId,
                title = noteTitle.value,
                content = noteContent.value,
                timestamp = System.currentTimeMillis(),
                color = noteColor.value,
                noteType = noteType.value
            )
            repository.insertNote(note)
        }
    }
}