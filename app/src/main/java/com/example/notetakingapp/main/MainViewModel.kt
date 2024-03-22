package com.example.notetakingapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetakingapp.model.NoteModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes = repository.allNotes

    private val _selectedEditNote: MutableLiveData<NoteModel?> = MutableLiveData(null)
    val selectedEditNote: LiveData<NoteModel?> = _selectedEditNote

    fun insert(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.insert(noteModel)
        }
    }

    fun delete(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.delete(noteModel)
        }
    }

    fun onEditNote(noteModel: NoteModel) {
        _selectedEditNote.postValue(noteModel)
    }

    fun handleButtonSaveEditOnClicked(title: String, content: String) {
        if (title.isNotEmpty() &&  content.isNotEmpty()) {
            val noteModel = NoteModel(
                title = title,
                content = content,
            )
            if (selectedEditNote.value == null) {
                insert(noteModel)
            } else {
                selectedEditNote.value?.title = title
                selectedEditNote.value?.content = content
                update(selectedEditNote.value!!)
            }
            _selectedEditNote.postValue(null)

        }
    }

    fun update(note: NoteModel) { // Add update method
        viewModelScope.launch {
            repository.update(note)
        }
    }
}