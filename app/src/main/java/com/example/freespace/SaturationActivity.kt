package com.example.freespace
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.MyPlaceAdapter
import com.example.freespace.databinding.ActivitySaturationBinding

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SaturationActivity : AppCompatActivity() {
    lateinit var binding: ActivitySaturationBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPlaceAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaturationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {
        val intent = intent
        val placeSat = intent.getIntExtra("int", -1)
        val placeName = intent.getStringExtra("string")

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        val query = rdb.orderByKey()
        val option = FirebaseRecyclerOptions.Builder<Place>().setQuery(query, Place::class.java).build()
        adapter = MyPlaceAdapter(option)
        adapter.itemClickListener = object : MyPlaceAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                val intent = Intent(this@SaturationActivity, PlaceActivity::class.java)
                intent.putExtra("string", adapter.getItem(position).pName.toString())
                intent.putExtra("string2", adapter.getItem(position).pInfo.toString())
                intent.putExtra("int", (adapter.getItem(position).pNum.toString().toDouble()/adapter.getItem(position).pMaxNum.toString().toDouble()*100).toInt())
                startActivity(intent)
            }
        }
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            placename.text = placeName
            placesaturation.text = "$placeSat%"
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