package com.example.note_app.di

import android.content.Context
import androidx.room.Room
import com.example.note_app.data.local.dao.NoteDao
import com.example.note_app.data.local.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).build()

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao =
        database.noteDao()
}
