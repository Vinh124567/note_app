package com.example.note_app.presentation.dashboard_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note_app.domain.model.Note
import com.example.note_app.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = mutableStateOf(ActionListState())
    val state: State<ActionListState> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    fun onEvent(event: ActionListEvent) {
        when (event) {
            is ActionListEvent.DeleteAction -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is ActionListEvent.RestoreAction -> {
                viewModelScope.launch {
                    repository.insertNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = repository.getAllNote()
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes.sortedByDescending { it.timestamp }
                )
            }
            .launchIn(viewModelScope)
    }
}