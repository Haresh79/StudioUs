package com.example.studious

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studious.models.Post
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class homeFrag : Fragment() {
    lateinit var picpostlist:MutableList<postData>
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView=view.findViewById(R.id.homeRv)

        var post:Post=Post()
        picpostlist= mutableListOf()
        val adapter=MyAdapter(picpostlist)
        val myAdapter=adapter
        Firebase.firestore.collection("Post").get().addOnSuccessListener {
            for (i in it){
                post=i.toObject<Post>()
                picpostlist.add(postData(post.image,post.profile,post.idName,post.caption,post.like,post.timeStamp!!))
                picpostlist.sortByDescending {
                    it.timeStamp
                }
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=myAdapter

        return view
    }

}