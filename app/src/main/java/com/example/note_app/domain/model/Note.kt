package com.example.note_app.domain.model

import com.example.note_app.data.local.entity.NoteType

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val noteType: NoteType,
    val isSelected: Boolean = false,
    val reminderTime: Long? = null

) {
    companion object {
        val noteColors = listOf(
            0xFFFFAB91.toInt(),
            0xFFFFCC80.toInt(),
            0xFFE6EE9C.toInt(),
            0xFF80DEEA.toInt(),
            0xFFCF94DA.toInt(),
            0xFFF48FB1.toInt()
        )
    }
}