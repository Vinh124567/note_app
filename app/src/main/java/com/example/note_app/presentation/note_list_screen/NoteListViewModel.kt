package com.example.note_app.presentation.note_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note_app.data.local.entity.NoteType
import com.example.note_app.domain.model.Note
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

    private fun getNotesByType(type: NoteType) {
        getNotesJob?.cancel()
        getNotesJob = repository.getNoteByType(type)
            .onEach { notes ->
                _state.value = state.value.copy(notes = notes)
            }
            .launchIn(viewModelScope)
    }
}