package com.example.notetaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.activity_register.*

class AddNoteActivity : AppCompatActivity() {

    val database = Firebase.database.reference
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        btn_Create_Note.setOnClickListener {
            putNoteInFirebase()
        }
    }

    private fun putNoteInFirebase() {

        var userId=auth.currentUser?.uid
        val note = Note()
        note.noteTitle = et_Title.text.toString()
        note.noteContent = et_Create_Note.text.toString()
        var noteID = database.push().key
        if (userId != null) {
            if (noteID != null){
                var rootRef =  database.child("users").child(userId).child("Notes").child(noteID)
                rootRef.setValue(note).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext,"Note Successfully Created",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else{
                        Toast.makeText(baseContext,"Some Error Occurred: "+task.exception,Toast.LENGTH_SHORT).show()
                    }
                } //Database structure is: /Root/Users/UserID/Notes/NoteID/...
            }
        }
    }
}
