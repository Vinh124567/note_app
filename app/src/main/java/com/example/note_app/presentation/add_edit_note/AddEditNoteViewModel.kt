package com.example.note_app.presentation.add_edit_note

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note_app.domain.model.Note
import com.example.note_app.domain.repository.NoteRepository
import com.example.note_app.util.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.note_app.data.local.entity.NoteType

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteId = mutableStateOf<Int?>(
        savedStateHandle.get<Int>("noteId")?.let {
            if (it == -1) null else it
        }
    )
    private val noteId: Int? get() = _noteId.value

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    private val _noteColor = mutableStateOf(0xFFBAE1FF.toInt())
    val noteColor: State<Int> = _noteColor

    private val _noteType = mutableStateOf(NoteType.PERSONAL)
    val noteType: State<NoteType> = _noteType

    private val _originalTimestamp = mutableStateOf<Long?>(null)

    private val _showTypeSelector = mutableStateOf(false)
    val showTypeSelector: State<Boolean> = _showTypeSelector

    private val _showColorPicker = mutableStateOf(false)
    val showColorPicker: State<Boolean> = _showColorPicker

    private val _reminderTime = mutableStateOf<Long?>(null)
    val reminderTime: State<Long?> = _reminderTime

    val isEditMode: Boolean get() = noteId != null

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
                _reminderTime.value = note.reminderTime
                _originalTimestamp.value = note.timestamp
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

    fun onReminderTimeChange(time: Long?) {
        _reminderTime.value = time
    }

    fun saveNote() {
        viewModelScope.launch {
            // 如果更新笔记，先取消旧的通知
            noteId?.let { id ->
                NotificationHelper.cancelNotification(context, id)
            }

            // 如果是编辑模式，保留原来的时间戳；如果是新建，使用当前时间
            val timestamp = if (noteId != null && _originalTimestamp.value != null) {
                _originalTimestamp.value!!
            } else {
                System.currentTimeMillis()
            }
            
            val note = Note(
                id = noteId,
                title = noteTitle.value,
                content = noteContent.value,
                timestamp = timestamp,
                color = noteColor.value,
                noteType = noteType.value,
                reminderTime = reminderTime.value
            )
            
            repository.insertNote(note)
            
            // 如果设置了提醒时间，安排通知
            // 对于新笔记，使用一个临时ID（实际应用中应该从数据库获取）
            reminderTime.value?.let { reminder ->
                val idToUse = noteId ?: (System.currentTimeMillis().toInt() % Int.MAX_VALUE)
                NotificationHelper.scheduleNotification(
                    context = context,
                    noteId = idToUse,
                    title = noteTitle.value,
                    content = noteContent.value,
                    reminderTime = reminder
                )
            }
        }
    }
}