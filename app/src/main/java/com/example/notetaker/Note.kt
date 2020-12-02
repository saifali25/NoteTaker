package com.example.notetaker

import com.google.firebase.database.Exclude

class Note () {
    var noteContent = ""
    var noteTitle = ""
    @Exclude
    var noteID = ""

}