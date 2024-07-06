package com.example.studious

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studious.models.User
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val logEmail=findViewById<EditText>(R.id.emailEt)
        val logPass=findViewById<EditText>(R.id.passEt)

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            if (logEmail.text.isNotEmpty() && logPass.text.isNotEmpty()){
                var user=User(logEmail.text.toString(), logPass.text.toString())
                FirebaseAuth.getInstance().signInWithEmailAndPassword(user.Email!!, user.Pass!!).addOnSuccessListener {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            }

        }
        findViewById<TextView>(R.id.goRegBtn).setOnClickListener {
            startActivity(Intent(this,registerActivity::class.java))
        }
    }
}