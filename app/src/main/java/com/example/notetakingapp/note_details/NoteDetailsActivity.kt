package com.example.notetakingapp.note_details

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.notetakingapp.databinding.ActivityNoteDetailsBinding
import com.example.notetakingapp.main.getViewModel
import com.example.notetakingapp.model.NoteModel

class NoteDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailsBinding
    private lateinit var viewModel: NoteDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val note = intent.getSerializableExtra(EXTRA_NOTE) as? NoteModel

        viewModel =  getViewModel {
            NoteDetailsViewModel(note)
        }


        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // Show back button in ActionBar
            setDisplayShowHomeEnabled(true)
        }

        viewModel.note.observe(this) { updatedNote ->
            binding.textViewTitle.text = updatedNote?.title
            binding.textViewDescription.text = updatedNote?.content
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
    }
}