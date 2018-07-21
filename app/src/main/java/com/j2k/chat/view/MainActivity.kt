package com.j2k.chat.view

import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.*
import com.j2k.chat.R
import com.j2k.chat.model.Chat
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var id:String? = null
    private var firebaseDatabase:FirebaseDatabase? = null
    private var databaseReference:DatabaseReference? = null
    var adapter:ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        initListView()
        initDatabase()

        sendBtn.setOnClickListener {
            val date:String = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())
            val chat = Chat(id!!, msgTxt.text.toString(), date)
            databaseReference!!.child("message").push().setValue(chat)
            msgTxt.text.clear()
        }

    }

    private fun initDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference
        setEventListener()
    }

    private fun initListView() {
        listView.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(this)
        listView.adapter = adapter
    }

    private fun setEventListener() {
        databaseReference!!.child("message").addChildEventListener(object : ChildEventListener{
            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(dataSnapshot: DatabaseError) {

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                var chat = dataSnapshot.getValue(Chat::class.java)
                adapter?.data?.add(chat)

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }
        })
    }

}
