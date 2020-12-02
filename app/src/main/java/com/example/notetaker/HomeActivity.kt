package com.example.notetaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.note_card.*

open class HomeActivity : AppCompatActivity() {

    var nList = ArrayList<Note>()
    var note = Note()

    private lateinit var recyclerView: RecyclerView

    val rootReference = Firebase.database.reference //app root in firebase database
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Read notes from database
        readNotesFromFirebaseDatabase()

        //Show user's name in welcome message
        //get the name of user from firebase
        val nameFromFirebase: FirebaseDatabase = FirebaseDatabase.getInstance()

        var nameReference = rootReference.child("users").child(uid).child("name")
        nameReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = snapshot.value
                tv_User_Name.text = result.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //Read notes from database
        readNotesFromFirebaseDatabase()

        btn_createNote.setOnClickListener {
            Intent(this, AddNoteActivity::class.java).also {
                startActivity(it)
            }
        }
        readNotesFromFirebaseDatabase()


        //Updating Layout to display notes in RecyclerView
        recyclerView = findViewById<RecyclerView>(R.id.rv_displayNotesInRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //RecyclerView Adapter being passed the notes list
        val adapter = NotesAdapter(nList)
        rv_displayNotesInRecyclerView.adapter = adapter

    }


    private fun readNotesFromFirebaseDatabase() {
        val noteReference = rootReference.child("users").child(uid).child("Notes")
        noteReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val noteID = snapshot.key

                //Add Notes to the ArrayList of Notes
                if (noteID != null) {
                    for (noteSnap in snapshot.children){
                        note = noteSnap.getValue(Note::class.java)!!
                        note.noteContent = noteSnap.child("noteContent").getValue(String::class.java).toString()
                        note.noteTitle = noteSnap.child("noteTitle").getValue(String::class.java).toString()
                        nList.add(note)
                    }
                   }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Error Occurred: $error",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
