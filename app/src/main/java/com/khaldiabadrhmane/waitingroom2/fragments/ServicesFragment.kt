package com.khaldiabadrhmane.waitingroom2.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.khaldiabadrhmane.waitingroom2.R
import com.khaldiabadrhmane.waitingroom2.model.Servces
import com.khaldiabadrhmane.waitingroom2.recyclerview.ServcesItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_services.*


class ServicesFragment : Fragment() {

    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    val firestoreInstance: FirebaseFirestore by lazy {

        FirebaseFirestore.getInstance()
    }
    private lateinit var ServicesSection: Section


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val textViewTitle= activity?.findViewById(R.id.Toolbar_main_text) as TextView
        textViewTitle.text="Liste des services"
        addChatListener(::initRecyclerView)
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    private fun addChatListener(onListen: (List<com.xwray.groupie.kotlinandroidextensions.Item>) -> Unit): ListenerRegistration {

        return firestoreInstance.collection("Services").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            val items = mutableListOf<com.xwray.groupie.kotlinandroidextensions.Item>()
            value!!.documents.forEach { document ->
                val s = document.id
                var ss= document.toObject(Servces::class.java)
                if (ss != null) {
                    items.add(ServcesItem(s, ss, requireActivity()))

                }else{
                    items.add(ServcesItem(s, Servces("aaaa","bbbb","ccc"), requireActivity()))
                }

            }
            onListen(items)


        }

    }

    private fun initRecyclerView(items: List<com.xwray.groupie.kotlinandroidextensions.Item>) {
        Servces_recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = GroupAdapter<ViewHolder>().apply {
                ServicesSection = Section(items)
                add(ServicesSection)

            }


        }
    }


}

