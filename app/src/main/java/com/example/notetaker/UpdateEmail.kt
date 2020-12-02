package com.example.notetaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_update_email.*
import kotlinx.android.synthetic.main.activity_user_account.btnUpdate_Email

class UpdateEmail : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)

        btnUpdate_Email.setOnClickListener {
            updateEmailInFirebase()
        }

        btnCancelinMail.setOnClickListener {
            finish()
        }
    }

    private fun updateEmailInFirebase(){

            val oldEmailInfo = oldEmail.text.toString()
            val newEmaii = newEmail.text.toString()

            //To use Main Activity's method
            val pass = LoginActivity()

            if (pass.checkIfEmailIsValid(oldEmail))
                //Update in firebase
                auth!!.updateEmail(newEmaii).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email Updated Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error: "+task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
