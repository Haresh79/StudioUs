package com.example.studious

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studious.models.User
import com.squareup.picasso.Picasso

class MySecAdapter(val profileData: MutableList<User>):RecyclerView.Adapter<MySecAdapter.viewHolder>() {
    class viewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val profile=view.findViewById<ImageView>(R.id.searchIdLogo)
        val idName=view.findViewById<TextView>(R.id.searchIdText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.search_id_item,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileData.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Picasso.get().load(profileData[position].image).into(holder.profile)
        holder.idName.text=profileData[position].Uname
    }
}