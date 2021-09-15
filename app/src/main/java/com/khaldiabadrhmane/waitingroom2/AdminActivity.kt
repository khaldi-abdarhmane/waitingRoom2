package com.khaldiabadrhmane.waitingroom2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.khaldiabadrhmane.waitingroom2.model.Servces
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.ByteArrayOutputStream
import java.util.*

class AdminActivity : AppCompatActivity(), TextWatcher {
     var  mPath:String =""
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    val mcurrentUserId= mAuth.currentUser!!.uid
    private val storageInstance by lazy {

        FirebaseStorage.getInstance()

    }
    private val currentUserStorageRef: StorageReference
    get() = storageInstance.reference.child("profile:"+mAuth.currentUser?.uid.toString())
    companion object{
        val RC_SELECT_IMAGE =3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        editText_name_new_services.addTextChangedListener(this)
        editText_destripution_new_services.addTextChangedListener(this)
        editText_type_new_services.addTextChangedListener(this)

        btn_new_services.setOnClickListener {
            createChatChannel()

        }

        imageView_icon_new_services.setOnClickListener {

            val myIntentimage= Intent().apply {
                type="image/*"
                action= Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg","image/png"))

            }
            startActivityForResult(Intent.createChooser(myIntentimage,"select Image"), AdminActivity.RC_SELECT_IMAGE)


        }

    }





    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        btn_new_services.isEnabled= editText_name_new_services.text.trim().isNotEmpty()
                && editText_type_new_services.text.trim().isNotEmpty()
                && editText_destripution_new_services.text.trim().isNotEmpty()
    }

    override fun afterTextChanged(s: Editable?) {

    }


    private fun createChatChannel(){


                    val newServces:Servces=Servces(editText_name_new_services.text.toString(),editText_type_new_services.text.toString(),editText_destripution_new_services.text.toString(),mPath)
                    val ref= firestoreInstance.collection("users")
                            .document("user:" + mcurrentUserId)
                            .collection("ServicesSet")
                            .document()


                   ref.set(newServces)
                   firestoreInstance.document("Services/${ref.id.toString()}").set(newServces)

    }


    private fun uploadProfileImage(seletedImageBytes: ByteArray, OnSuccess:(imagePath:String)->Unit ) {

        val ref= currentUserStorageRef.child("ServcesPictures/${UUID.nameUUIDFromBytes(seletedImageBytes)}")
        ref.putBytes(seletedImageBytes).addOnCompleteListener {
            task ->
            if (task.isSuccessful){

                OnSuccess(ref.path)
                progressBar_Servces.visibility= View.GONE


            }else{
                Toast.makeText(this,"ERROR : ${task.exception?.message }", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AdminActivity.RC_SELECT_IMAGE && resultCode== Activity.RESULT_OK  && data?.data !=null ){

            progressBar_Servces.visibility=View.VISIBLE


            imageView_icon_new_services.setImageURI(data.data)
            val selectedImagePath = data.data
            val selectedImageBmp= MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImagePath)
            val outputStorage= ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG,20,outputStorage)
            val seletedImageBytes=outputStorage.toByteArray()

            uploadProfileImage(seletedImageBytes){

                path ->
            mPath=path




            }



        }

    }



}