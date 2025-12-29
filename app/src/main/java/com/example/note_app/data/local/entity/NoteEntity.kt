package com.example.note_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.note_app.domain.model.Note

enum class NoteType {
    PERSONAL,
    WORK,
    STUDY,
    SHOPPING,
    REMINDER
}

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val noteType: NoteType,
    val reminderTime: Long? = null
) {
    fun toNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            timestamp = timestamp,
            color = color,
            noteType = noteType,
            reminderTime = reminderTime
        )
    }
}


fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color,
        noteType = noteType,
        reminderTime = reminderTime
    )
}