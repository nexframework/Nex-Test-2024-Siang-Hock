package com.example.notetakingapp.main

import NoteAdapter
import com.example.notetakingapp.note_details.NoteDetailsActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetakingapp.databinding.ActivityMainBinding
import com.example.notetakingapp.model.NoteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel  by lazy {
        getViewModel {
            val applicationScope = CoroutineScope(SupervisorJob())
            val database by lazy { NoteDatabase.getDatabase(this, applicationScope) }
            val repository by lazy { NoteRepository(database.noteDao()) }
            MainViewModel(repository)
        }
    }
    private lateinit var adapter: NoteAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        adapter = NoteAdapter({ clickedNote ->
            val intent = Intent(this, NoteDetailsActivity::class.java)
            intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, clickedNote)
            startActivity(intent)
        },{ onDeletedNote ->
            viewModel.delete(onDeletedNote)
        },{ onEditNote ->
            viewModel.onEditNote(onEditNote)
        })

        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launchWhenStarted {
            viewModel.allNotes.collect { notes ->
                adapter.setNotes(notes)
            }
        }

        binding.buttonAddNote.setOnClickListener {
            viewModel.handleButtonSaveEditOnClicked(binding.editTextTitle.text.toString(), binding.editTextNote.text.toString())
        }

        viewModel.selectedEditNote.observe(this) { note ->
            binding.editTextTitle.setText(note?.title ?: "")
            binding.editTextNote.setText(note?.content ?: "")
            binding.buttonAddNote.text = if (note == null) "Add" else "Update"
        }
    }
}