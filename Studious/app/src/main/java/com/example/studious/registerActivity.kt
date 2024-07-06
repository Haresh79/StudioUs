package com.example.studious

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studious.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class registerActivity : AppCompatActivity() {
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        val userName=findViewById<EditText>(R.id.unEt)
        val userClass=findViewById<EditText>(R.id.cEt)
        val userMail=findViewById<EditText>(R.id.eEt)
        val userPassword=findViewById<EditText>(R.id.pEt)
        user= User()

        var email: String? =null
        var password: String? =null
        var url: String? =null

        if (intent.getStringExtra("MODE")=="e"){

            findViewById<TextView>(R.id.textView).text="EDIT PROFILE"
            findViewById<Button>(R.id.registerBtn).text="Update"
            findViewById<EditText>(R.id.pEt).visibility=View.GONE
            findViewById<EditText>(R.id.eEt).hint="Double Click to Set Profile"
            findViewById<EditText>(R.id.eEt).keyListener=null
            val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri!=null){
                    uploadProfileImg(uri){
                        url=it
                        findViewById<EditText>(R.id.eEt).setText(url)
                    }
                }else{
                    url=null
                }
            }
            findViewById<EditText>(R.id.eEt).setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                findViewById<EditText>(R.id.eEt).hint="Wait For Uploading..."
            }
            Firebase.firestore.collection("User").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                user= it.toObject<User>()!!
                userName.setText(user.Uname)
                userClass.setText(user.Uclass)
                email=user.Email.toString()
                password=user.Pass.toString()
                if (url.equals(null)){
                    url=user.image.toString()
                }
            }
        }
        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            if (intent.getStringExtra("MODE")=="e"){
                if (userName.text.isNotEmpty() && userClass.text.isNotEmpty()){
                    user.Uname=userName.text.toString()
                    user.Uclass=userClass.text.toString()
                    user.Email=email
                    user.Pass=password
                    user.image=url
                    Firebase.firestore.collection("User").document(FirebaseAuth.getInstance().currentUser!!.uid).set(user).addOnSuccessListener {
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                }
            }else{
                if (userName.text.isNotEmpty() && userClass.text.isNotEmpty() && userMail.text.isNotEmpty() && userPassword.text.isNotEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(userMail.text.toString(), userPassword.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            user.Email=userMail.text.toString()
                            user.Uname=userName.text.toString()
                            user.Uclass=userClass.text.toString()
                            user.Pass=userPassword.text.toString()
                            user.image="https://jeffjbutler.com/wp-content/uploads/2018/01/default-user.png"
                            Firebase.firestore.collection("User").document(Firebase.auth.currentUser!!.uid).set(user)
                            startActivity(Intent(this,loginActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this,it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }



        }
    }
    fun uploadProfileImg(uri: Uri, callback:(String?)->Unit) {
        var retunUrl: String? =null
        FirebaseStorage.getInstance().getReference("profilepics").child(UUID.randomUUID().toString()).putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                retunUrl=it.toString()
                callback(retunUrl)
            }
        }
    }
}