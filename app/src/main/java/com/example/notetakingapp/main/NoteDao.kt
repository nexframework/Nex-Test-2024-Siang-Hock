package com.example.notetakingapp.main

import androidx.room.*
import com.example.notetakingapp.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteModel>>

    @Insert
    suspend fun insert(noteModel: NoteModel)

    @Delete
    suspend fun delete(noteModel: NoteModel)

    @Update
    suspend fun update(note: NoteModel)
}
