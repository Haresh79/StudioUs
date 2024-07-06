package com.example.studious

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val picpostlist: List<postData>) : RecyclerView.Adapter<MyAdapter.viewHoldeer>() {
    class viewHoldeer(val view:View):RecyclerView.ViewHolder(view) {
        val profile=view.findViewById<ImageView>(R.id.profileLogo)
        val idName=view.findViewById<TextView>(R.id.idname)
        val caption=view.findViewById<TextView>(R.id.postCaption)
        val post=view.findViewById<ImageView>(R.id.postImg)
        val likes=view.findViewById<ImageView>(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHoldeer {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.home_post_item,parent,false)

        return viewHoldeer(view)
    }

    override fun getItemCount(): Int {
        return picpostlist.size
    }

    override fun onBindViewHolder(holder: viewHoldeer, position: Int) {
        holder.caption.text=picpostlist[position].caption
        holder.idName.text=picpostlist[position].id
        Picasso.get().load(picpostlist[position].link).into(holder.post)
        Picasso.get().load(picpostlist[position].prifile).into(holder.profile)
        holder.likes.setOnClickListener {
            holder.likes.setImageResource(R.drawable.alike)
        }
    }

}
