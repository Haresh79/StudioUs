package com.example.studious

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.studious.models.Post
import com.example.studious.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.squareup.picasso.Picasso

class profileFrag : Fragment() {
    lateinit var postlist:MutableList<postData>
    lateinit var user: User
    lateinit var post: Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        val gridView=view.findViewById<GridView>(R.id.picGrid)

        Firebase.firestore.collection("User").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            user= it.toObject<User>()!!
            view.findViewById<TextView>(R.id.textView2).text=user.Uname
            view.findViewById<TextView>(R.id.textView3).text="Class: "+user.Uclass
            view.findViewById<TextView>(R.id.textView4).text="0 Joins"
            Picasso.get().load(user.image).into(view.findViewById<ImageView>(R.id.profilePic))
        }


        post=Post()
        postlist= mutableListOf()
        val adapter=gridAdapter(postlist)
        gridView.adapter=adapter
        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            for (i in it){
                post=i.toObject<Post>()
                postlist.add(postData(post.image, post.profile, post.idName, post.caption, post.like,
                    post.timeStamp!!
                ))
                postlist.sortByDescending{
                    it.timeStamp
                }
                adapter.notifyDataSetChanged()
            }
        }


        view.findViewById<Button>(R.id.button).setOnClickListener {
            val intent=Intent(context, registerActivity::class.java)
            intent.putExtra("MODE","e")
            startActivity(intent)
        }

        return view
    }


}