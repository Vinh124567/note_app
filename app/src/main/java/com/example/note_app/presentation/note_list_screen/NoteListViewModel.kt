package com.example.note_app.presentation.note_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note_app.data.local.entity.NoteType
import com.example.note_app.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var getNotesJob: Job? = null

    private val _state = mutableStateOf(NoteListState())
    val state: State<NoteListState> = _state

    private val noteType: NoteType =
        savedStateHandle.get<String>("noteType")
            ?.let { NoteType.valueOf(it) }
            ?: NoteType.STUDY

    init {
        getNotesByType(noteType)
    }

    fun deleteSelectedNotes() {
        val notesToDelete = _state.value.selectedNotes.toList()
        viewModelScope.launch {
            notesToDelete.forEach { noteId ->
                val note = repository.getNoteById(noteId)
                if (note != null) {
                    repository.deleteNote(note)
                }
            }
            exitSelectionMode()
        }
    }


    fun onNoteLongPress(noteId: Int) {
        _state.value = _state.value.copy(
            isSelectionMode = true,
            selectedNotes = setOf(noteId)
        )
    }

    fun onSelectNote(noteId: Int) {
        val currentSet = _state.value.selectedNotes
        val newSet = if (currentSet.contains(noteId)) {
            currentSet - noteId
        } else {
            currentSet + noteId
        }

        _state.value = _state.value.copy(
            selectedNotes = newSet
        )
    }


    fun exitSelectionMode() {
        _state.value = _state.value.copy(
            isSelectionMode = false,
            selectedNotes = emptySet()
        )
    }

    private fun getNotesByType(type: NoteType) {
        getNotesJob?.cancel()
        val query = _state.value.searchQuery
        getNotesJob = if (query.isNotEmpty()) {
            repository.searchNotesByType(type, query)
        } else {
            repository.getNoteByType(type)
        }
            .onEach { notes ->
                _state.value = _state.value.copy(notes = notes)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        getNotesByType(noteType)
    }

    fun onSearchActiveChange(isActive: Boolean) {
        _state.value = _state.value.copy(isSearchActive = isActive)
        if (!isActive) {
            _state.value = _state.value.copy(searchQuery = "")
            getNotesByType(noteType)
        }
    }
}