package com.example.notetaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val currentUser = auth.currentUser

        Handler().postDelayed({
            if (currentUser != null)
            //User is already logged in
                Intent(this,HomeActivity::class.java).also {
                    startActivity(it)
                }
            else
            //User is not logged in, then show Login Activity
                Intent(this,LoginActivity::class.java).also {
                    startActivity(it)
                }

        }, 3000) //3 seconds

    }
}