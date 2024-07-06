package com.example.studious

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.VideoView
import androidx.lifecycle.LifecycleOwner
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class gridAdapter(val data: MutableList<postData>):BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view:View
        val viewHolder=viewHolder()

            view=LayoutInflater.from(parent!!.context).inflate(R.layout.grid_item_pic,parent,false)
            viewHolder.imageView=view.findViewById(R.id.post)
            Picasso.get().load(data[position].link).into(viewHolder.imageView)

        return view
    }

    private class viewHolder(){
        lateinit var imageView: ImageView
    }
}