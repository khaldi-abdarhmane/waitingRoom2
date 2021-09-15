package com.khaldiabadrhmane.waitingroom2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.khaldiabadrhmane.waitingroom2.R


class NewTicketFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val textViewTitle= activity?.findViewById(R.id.Toolbar_main_text) as TextView
        textViewTitle.text="Liste Ticket"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_ticket, container, false)
    }

}