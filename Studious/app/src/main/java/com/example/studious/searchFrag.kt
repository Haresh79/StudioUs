package com.example.studious

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studious.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class searchFrag : Fragment() {
    lateinit var profileList:MutableList<User>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView=view.findViewById(R.id.searchRv)

        profileList= mutableListOf()
        Firebase.firestore.collection("User").get().addOnSuccessListener {
            for (i in it){
                var user: User = i.toObject<User>()
                profileList.add(user)
            }
            findProfiles("")
        }

        recyclerView.layoutManager=LinearLayoutManager(context)
        view.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            val inputName=view.findViewById<EditText>(R.id.editTextText).text.toString()
            findProfiles(inputName)
        }


        return view
    }

    private fun findProfiles(inputName: String) {
        val mutablelist:MutableList<User> = mutableListOf()
        for (i in 0..profileList.size-1){
            if (profileList[i].Uname!!.contains(inputName, ignoreCase = true)){
                mutablelist.add(profileList[i])
            }
        }
        recyclerView.adapter=MySecAdapter(mutablelist)
    }
}