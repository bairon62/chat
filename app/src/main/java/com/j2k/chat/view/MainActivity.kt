package com.j2k.chat.view

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.*
import com.j2k.chat.R
import com.j2k.chat.model.Chat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var id:String? = null
    private var firebaseDatabase:FirebaseDatabase? = null
    private var databaseReference:DatabaseReference? = null
    var adapter:ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        //listView.layoutManager = LinearLayoutManager(this)
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1)
        listView.adapter = adapter
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference

        setEventListener()
        sendBtn.setOnClickListener {
            val chat = Chat(id!!, msgTxt.text.toString(), Date().toString())
            databaseReference!!.child("message").push().setValue(chat)
            msgTxt.text.clear()
        }

    }

    private fun setEventListener() {
        databaseReference!!.child("message").addChildEventListener(object : ChildEventListener{
            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(dataSnapshot: DatabaseError) {

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                var chat = dataSnapshot.getValue(Chat::class.java)
                adapter?.add(chat?.message)

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }
        })
    }

}
