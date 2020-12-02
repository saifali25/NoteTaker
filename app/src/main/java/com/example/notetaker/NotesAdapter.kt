package com.example.notetaker

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_card.view.*


class NotesAdapter(private var noteView: ArrayList<Note>):
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    var ID = AddNoteActivity()

    inner class MyViewHolder(noteView: View) : RecyclerView.ViewHolder(noteView){

        init {
            itemView.setOnClickListener {
                val position :Int = adapterPosition
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val noteView : View = LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false)
        return MyViewHolder(noteView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Set data here
        var note = Note()
        note = noteView[position]
        holder.itemView.apply {
           note_title_TV.text = noteView[position].noteTitle

           note_content_TV.text = noteView[position].noteContent

           btn_edit.setOnClickListener {
               val intent = Intent(context, EditNoteActivity::class.java)
               intent.putExtra("noteTitle", note.noteTitle)
               intent.putExtra("noteContent",note.noteContent)
               intent.putExtra("noteID", noteView[position].noteID)
               context.startActivity(intent)

           }

           btn_delete.setOnClickListener {
               Intent(context, DeleteNoteActivity::class.java).also {
                   context.startActivity(it)
               }
           }
       }

       }
    override fun getItemCount(): Int {
        return noteView.size
    }

}