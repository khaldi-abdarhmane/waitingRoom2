package com.khaldiabadrhmane.waitingroom2

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.khaldiabadrhmane.waitingroom2.fragments.NewTicketFragment
import com.khaldiabadrhmane.waitingroom2.fragments.ServicesFragment
import com.khaldiabadrhmane.waitingroom2.fragments.TicketTrashFragment
import com.khaldiabadrhmane.waitingroom2.glide.GlideApp
import com.khaldiabadrhmane.waitingroom2.model.User
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

         private val mServicesFragment=ServicesFragment()
         private val mNewTicketFragment=NewTicketFragment()
         private val mTicketTrashFragment=TicketTrashFragment()


        private val mAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }
        val firestoreInstance: FirebaseFirestore by lazy {

            FirebaseFirestore.getInstance()
        }
        private val storageInstance by lazy {

            FirebaseStorage.getInstance()

        }
        private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/user:${mAuth.currentUser?.uid.toString() }")
        private val currentUserStorageRef: StorageReference
        get() = storageInstance.reference.child("profile:"+mAuth.currentUser?.uid.toString())



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            setSupportActionBar(toolbar_main)
            supportActionBar?.title=""

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }else{
                window.statusBarColor= Color.WHITE
            }
            setFragment(mServicesFragment)

            bottomNavigationView_main.setOnNavigationItemSelectedListener(this)

            currentUserDocRef.get().addOnSuccessListener {
                val user=it.toObject(User::class.java)
                if (user!!.pathimage.isNotEmpty()){
                    GlideApp.with(this)
                            .load(storageInstance.getReference(user.pathimage))
                            .placeholder(R.drawable.ic__image_24)
                            .into(id_circleImageView_profile_image)
                }else{
                    id_circleImageView_profile_image.setImageResource(R.drawable.ic__image_24)
                }

            }


            id_circleImageView_profile_image.setOnClickListener {

                startActivity(Intent(this, ProfileActivity::class.java))

            }


            /*

            btn.setOnClickListener {


            mAuth.signOut()

                val intentsignInActivity= Intent(this,SignInActivity::class.java)
                intentsignInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intentsignInActivity)

            }

             */
        }




      override fun onNavigationItemSelected(item: MenuItem): Boolean {

          when(item.itemId) {



              R.id.navigation_services_item -> {
                  setFragment(mServicesFragment)

                  return true }


              R.id.navigation_newTicket_item -> {
                  setFragment(mNewTicketFragment)
                  return true }

              R.id.navigation_ticketTrash_item -> {

                  setFragment(mTicketTrashFragment)
                  return true}

              else -> return false

          }


      }


      private fun setFragment(fragment: Fragment) {

          val fr=supportFragmentManager.beginTransaction()
          fr.replace(R.id.coordinatorLayoutkhaldi,fragment)
          fr.commit()


      }



      override fun onStart() {
          super.onStart()

          currentUserDocRef.get().addOnSuccessListener {
              val user=it.toObject(User::class.java)
              if (user!!.pathimage.isNotEmpty()){
                  GlideApp.with(this)
                          .load(storageInstance.getReference(user.pathimage))
                          .placeholder(R.drawable.ic__image_24)
                          .into(id_circleImageView_profile_image)
              }else{
                  id_circleImageView_profile_image.setImageResource(R.drawable.ic__image_24)
              }

          }
      }



    }