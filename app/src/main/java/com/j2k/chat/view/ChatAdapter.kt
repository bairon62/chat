package com.j2k.chat.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.j2k.chat.R
import com.j2k.chat.model.Chat


class ViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
    var messageTxt = itemView.findViewById<TextView>(R.id.messageTxt)
    var dateTxt = itemView.findViewById<TextView>(R.id.dateTxt)

    fun bindHolder(data: Chat?) {
        nameTxt.text = data?.name
        messageTxt.text = data?.message
        dateTxt.text = data?.date
    }
}
class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var messageTxt = itemView.findViewById<TextView>(R.id.messageTxt)
    var dateTxt = itemView.findViewById<TextView>(R.id.dateTxt)

    fun bindHolder(data: Chat?) {
        messageTxt.text = data?.message
        dateTxt.text = data?.date
    }
}

class ChatAdapter(val context:Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<Chat?>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val chat = data[position]
        if(holder is ViewHolder0) {
            holder.bindHolder(chat)
        } else if(holder is ViewHolder1) {
            holder.bindHolder(chat)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val id = (context as MainActivity).id
        var type = 0
        if(data[position]?.name == id) {
            type = 1
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var viewHolder:RecyclerView.ViewHolder? = null
        when(viewType) {
            0 -> {
                val mainView = inflater.inflate(R.layout.layout_main_chat_0, parent, false)
                viewHolder = ViewHolder0(mainView)
                return viewHolder
            }
            1 -> {
                val mainView = inflater.inflate(R.layout.layout_main_chat_1, parent, false)
                viewHolder = ViewHolder1(mainView)
                return viewHolder
            }

        }
        return viewHolder

    }

    override fun getItemCount(): Int {
        return data.size
    }
}