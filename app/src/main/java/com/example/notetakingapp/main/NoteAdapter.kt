import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapp.databinding.ItemNoteBinding
import com.example.notetakingapp.model.NoteModel

class NoteAdapter(private val onItemClick: (NoteModel) -> Unit, private val onItemDeleteClick: (NoteModel) -> Unit,private val onItemEditClick: (NoteModel) ->
Unit) : RecyclerView.Adapter<NoteAdapter
.NoteViewHolder>() {

    private var notes = emptyList<NoteModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.bind(currentNote)
        holder.itemView.setOnClickListener {
            onItemClick(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: List<NoteModel>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteModel) {
            binding.model = note
            binding.buttonEditNote.setOnClickListener {
                onItemEditClick(note)
            }
            binding.buttonDeleteNote.setOnClickListener {
                onItemEditClick(note)
            }
            binding.executePendingBindings()
        }
    }
}