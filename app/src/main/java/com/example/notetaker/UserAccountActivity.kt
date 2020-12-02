package com.example.notetaker

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_account.*

class UserAccountActivity : AppCompatActivity() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)

        //Getting email of current user logged in
        tv_Welcome_Message.text = "Hi, "+auth.currentUser?.email.toString() + "! "

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        btnUpdate_Password.setOnClickListener {
            Toast.makeText(this@UserAccountActivity,"Update Password here", Toast.LENGTH_SHORT).show()
            Intent(this,UpdatePassword::class.java).also {
                startActivity(it)
            }
        }

        btnUpdate_Email.setOnClickListener {
            Toast.makeText(this@UserAccountActivity,"Update Email here", Toast.LENGTH_SHORT).show()
            Intent(this,UpdateEmail::class.java).also {
                startActivity(it)
            }
        }

    }

    fun showLogoutDialog(){
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Logout")
        //set message for alert dialog
        builder.setMessage("Do you want to Logout")
        //performing positive action
        builder.setPositiveButton("Yes, Logout"){dialogInterface, which ->
            auth.signOut()
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
            }
        }
        //performing cancel action
        builder.setNeutralButton("No"){dialogInterface , which ->
            dialogInterface.dismiss()
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}