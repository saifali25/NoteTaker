package com.example.notetaker

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    val mail = LoginActivity()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_SignIn.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
        val passInfo = Password.text.toString()
        val confirmPassInfo = confrimPass.text.toString()
        val nameOfUser = et_Name_Of_User.text


        btnRegister.setOnClickListener {
            if (nameOfUser.isEmpty()){
                Toast.makeText(baseContext, "Enter you name !!", Toast.LENGTH_SHORT).show()

            } else {
                if (mail.checkIfEmailIsValid(Email) && (mail.checkIfPasswordIsValid(Password)))
                    if (passInfo == confirmPassInfo)
                        //SignUp the user with email and password to firebase
                        auth.createUserWithEmailAndPassword(
                            Email.text.toString(),
                            Password.text.toString()
                        )
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    if (user != null) {
                                        Toast.makeText(baseContext, "Sign up was successful for: ${Email.text.toString()}", Toast.LENGTH_SHORT).show()
                                    }
                                    saveNameInFirebaseRealtimeDatabase()

                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(baseContext, "Error occurred: " + task.exception, Toast.LENGTH_SHORT).show()
                                }
                            }
                    else
                        Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveNameInFirebaseRealtimeDatabase(){
        database = Firebase.database.reference
        var userId=auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId).child("name").setValue(et_Name_Of_User.text.toString())
        }


    }
}