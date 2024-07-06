package com.example.studious

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
               if (FirebaseAuth.getInstance().currentUser==null){
                   startActivity(Intent(this,loginActivity::class.java))
                   finish()
               }else{
                   startActivity(Intent(this,MainActivity::class.java))
                   finish()
               }
        },3000)
    }
}