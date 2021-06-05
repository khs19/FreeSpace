package com.example.freespace

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.databinding.ActivityBookmarkSettingBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BookmarkSettingActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookmarkSettingBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: BookmarkAdapter
    lateinit var rdb: DatabaseReference
    var nameArray = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        val query = rdb.orderByChild("bookmark").equalTo(true.toString().toBoolean())
        val option = FirebaseRecyclerOptions.Builder<Place>().setQuery(query, Place::class.java).build()
        adapter = BookmarkAdapter(option)
        adapter.itemClickListener = object:BookmarkAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) {
                if(adapter.getItem(position).bookmarkSetting){
                    rdb.child(adapter.getItem(position).pName.toString()).child("bookmarkSetting").setValue(false.toString().toBoolean())
                    nameArray.add(adapter.getItem(position).pName)
                    adapter.startListening()
                }else {
                    rdb.child(adapter.getItem(position).pName.toString()).child("bookmarkSetting")
                        .setValue(true.toString().toBoolean())
                    nameArray.remove(adapter.getItem(position).pName)
                    adapter.startListening()
                }
            }
        }

        binding.apply {
            bookMarkRecyclerView.layoutManager = layoutManager
            bookMarkRecyclerView.adapter = adapter

            moveBookMark.setOnClickListener {
                for(i in 0..nameArray.size-1){
                    rdb.child(nameArray[i].toString()).child("bookmarkSetting").setValue(true.toString().toBoolean())
                }
                adapter.startListening()
                val intent = Intent(this@BookmarkSettingActivity, BookmarkActivity::class.java)
                startActivity(intent)
            }
            button.setOnClickListener {
                for(i in 0..nameArray.size-1){
                    rdb.child(nameArray[i].toString()).child("bookmark").setValue(false.toString().toBoolean())
                }
                adapter.startListening()
                val intent = Intent(this@BookmarkSettingActivity, BookmarkActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}