package com.example.notetakingapp.note_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notetakingapp.model.NoteModel

class NoteDetailsViewModel(private val initialNote: NoteModel?) : ViewModel() {

    private val _note = MutableLiveData(initialNote)
    val note: LiveData<NoteModel?> = _note
}
