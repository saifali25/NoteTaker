package com.example.notetaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        //Login Button
        btnLogin.setOnClickListener {

            if (checkIfEmailIsValid(et_log_Username) && checkIfPasswordIsValid(et_log_Password)){
                //Sign in user with email and password to firebase
                auth.signInWithEmailAndPassword(et_log_Username.text.toString(), et_log_Password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(baseContext, "User successfully logged in for: "+et_log_Username.text.toString(), Toast.LENGTH_SHORT).show()

                            Intent(this,HomeActivity::class.java).also {
                                startActivity(it)
                            }

                        } else {
                            Toast.makeText(baseContext, "Error occurred: "+task.exception, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this@LoginActivity, "Inputs are Invalid",Toast.LENGTH_LONG).show()
            }
        }

        //Register Button
        btnRegisterNow.setOnClickListener {
            Intent(this,RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

    //Email Address Validator
     fun checkIfEmailIsValid(email: EditText): Boolean {
        val emailInput = email.text.toString()
        if (emailInput.isEmpty()) {
            Toast.makeText(this, "Enter an email address", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(this, "Enter a Valid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            return true
        }
    }

    //Password Validator
    fun checkIfPasswordIsValid(passwordInput: EditText): Boolean {
        if (passwordInput.length()< 0) {
            Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show()
            return false
        } else if (passwordInput.length() < 6){
            Toast.makeText(this, "Enter a password of at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            return true
        }
    }

    //Show Progress Bar

}

