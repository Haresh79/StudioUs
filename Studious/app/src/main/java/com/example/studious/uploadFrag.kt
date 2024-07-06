package com.example.studious

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.studious.models.Post
import com.example.studious.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import java.util.UUID

class uploadFrag : Fragment() {

    lateinit var postUri:String
    lateinit var postCap:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_upload, container, false)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.loading)
                uploadPostImg(uri){
                    postUri=it.toString()
                    Picasso.get().load(postUri).into(view.findViewById<ImageView>(R.id.imageView2))
                }

            }
        }

        view.findViewById<ImageView>(R.id.imageView2).setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            postCap=view.findViewById<EditText>(R.id.editTextTextMultiLine).text.toString()
            if (postCap.isNotEmpty() || postUri!=null){
                //send data to server
                Firebase.firestore.collection("User").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                    var user=it.toObject<User>()
                    val post:Post= Post()
                    post.caption=postCap
                    post.image=postUri
                    post.idName= user!!.Uname
                    post.profile=user.image
                    post.timeStamp=System.currentTimeMillis().toString()
                    Firebase.firestore.collection("Post").document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid).document().set(post).addOnSuccessListener {
                            startActivity(Intent(context,MainActivity::class.java))
                        }
                    }
                }


                view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.baseline_upload_file_24)
                view.findViewById<EditText>(R.id.editTextTextMultiLine).text.clear()
            }
        }

        return view
    }
    fun uploadPostImg(uri: Uri, callback:(String?)->Unit) {
        var retunUrl: String? =null
        FirebaseStorage.getInstance().getReference("postpics").child(UUID.randomUUID().toString()).putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                retunUrl=it.toString()
                callback(retunUrl)
            }
        }
    }
}