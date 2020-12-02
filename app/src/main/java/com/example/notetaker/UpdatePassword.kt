package com.example.notetaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePassword : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)


        btnUpdate_Pass.setOnClickListener {
            updatePasswordInFirebase()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun updatePasswordInFirebase() {

        val newPassInfo = newPassword.text.toString()
        val confirmNewPassInfo = confrimNewPass.text.toString()

        //To use Main Activity's method
        val pass = LoginActivity()

        if (pass.checkIfPasswordIsValid(newPassword))
            if (newPassInfo == confirmNewPassInfo)
            //Update in firebase
            auth!!.updatePassword(newPassInfo).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error: "+task.exception, Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
    }
}
