package com.khaldiabadrhmane.waitingroom2.recyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.khaldiabadrhmane.waitingroom2.R
import com.khaldiabadrhmane.waitingroom2.glide.GlideApp
import com.khaldiabadrhmane.waitingroom2.model.Servces
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*


class ServcesItem (val uid:String,
                   val servces: Servces,
                   val context: Context):Item()
{

    private val storageInstance by lazy {

        FirebaseStorage.getInstance()

    }

    override fun bind(viewHolder:ViewHolder, position: Int) {



        viewHolder.itemView.item_textView_nameServces.text=servces.name
        viewHolder.itemView.item_textView_typeServces.text=servces.type
        viewHolder.itemView.item_textView_ouvert.text="ouvert"
        if (servces.img.isNotEmpty())
        {
            GlideApp.with(context)
                .load(storageInstance.getReference(servces.img))
                .placeholder(R.drawable.ic__image_24)
                .into(viewHolder.itemView.item_imageView_Servces)
        }else{
            viewHolder.itemView.item_imageView_Servces.setImageResource(R.drawable.ic__image_24)
        }
    }

    override fun getLayout(): Int {
        return R.layout.recycler_view_item
    }


}


/*

class ChatItem(
              val uid:
              val user:User,
              val context: Context
               ): Item() {
    private val storageInstance by lazy {

        FirebaseStorage.getInstance()

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.item_textView_person.text=user.name
        viewHolder.item_textView_caht_last.text="laste message ............"
        viewHolder.item_textView_date.text="12:21"
        if (user.pathimage.isNotEmpty())
        {
            GlideApp.with(context)
                    .load(storageInstance.getReference(user.pathimage))
                    .placeholder(R.drawable.ic_baseline_fingerprint_24)
                    .into(viewHolder.item_imageView_person)
        }else{
            viewHolder.item_imageView_person.setImageResource(R.drawable.ic_baseline_fingerprint_24)
        }
    }

    override fun getLayout(): Int {
        return R.layout.recycler_view_item
    }
}
 */