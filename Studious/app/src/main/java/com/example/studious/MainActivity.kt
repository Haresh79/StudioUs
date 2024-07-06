package com.example.studious

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val bottom=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFrag(homeFrag())
        bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFrag(homeFrag())
                R.id.search -> replaceFrag(searchFrag())
                R.id.upload -> replaceFrag(uploadFrag())
                R.id.profile -> replaceFrag(profileFrag())
                else ->{ }
            }
            true
        }
    }

    private fun replaceFrag(frag: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransition=fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frameLayout,frag)
        fragmentTransition.commit()
    }
}