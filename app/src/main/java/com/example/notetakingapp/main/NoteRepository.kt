package com.example.notetakingapp.main

import com.example.notetakingapp.model.NoteModel
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<NoteModel>> = noteDao.getAllNotes()

    suspend fun insert(noteModel: NoteModel) {
        noteDao.insert(noteModel)
    }

    suspend fun delete(noteModel: NoteModel) {
        noteDao.delete(noteModel)
    }

    suspend fun update(note: NoteModel) {
        noteDao.update(note)
    }
}