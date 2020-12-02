package com.example.notetaker

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_note.*

class EditNoteActivity : AppCompatActivity() {

    val database = Firebase.database.reference
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var n = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        //Getting data from the NotesAdapter Intent
        if (intent.extras != null) {
            val intent = intent
            val title = intent.getStringExtra("noteTitle")
            val content = intent.getStringExtra("noteContent")
            val id = intent.getStringExtra("noteID").toString()

            val setTitle = findViewById<TextView>(R.id.etEditTitle)
            val setContent = findViewById<TextView>(R.id.et_EditNote)

            setTitle.text = title
            setContent.text = content
            n = id

        }

        btn_EditNote.setOnClickListener {
            editNoteInFirebase()
        }
    }

    private fun editNoteInFirebase() {
        var userId = auth.currentUser?.uid

        if (userId != null) {
            var rootRef = database.child("users").child(userId).child("Notes")
            var noteID =rootRef.push().key
            var particularNoteReference = noteID?.let { rootRef.child(it) }
            if (particularNoteReference != null) {
                particularNoteReference.child("noteTitle").setValue(etEditTitle.text.toString())
                particularNoteReference.child("noteContent").setValue(et_EditNote.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Note Updated Successfully ", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error Occurred: " + task.exception, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    } //Database structure is: /Root/Users/UserID/Notes/NoteID/...
}
