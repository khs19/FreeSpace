package com.example.freespace

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
                intent.putExtra("placeName", adapter.getItem(position).pName.toString())
                intent.putExtra("placeInfo", adapter.getItem(position).pInfo.toString())
                intent.putExtra("bookmark", adapter.getItem(position).bookmark.toString().toBoolean())
                intent.putExtra("int", (adapter.getItem(position).pNum.toString().toDouble()/adapter.getItem(position).pMaxNum.toString().toDouble()*100).toInt())
                startActivity(intent)
            }
        }
        binding.apply {
            saturationRecyclerView.layoutManager = layoutManager
            saturationRecyclerView.adapter = adapter
            saturationPlace.text = placeName
            saturationPerscent.text = "$placeSat%"
            if(placeSat<30){
                saturationColor.setImageResource(R.drawable.outer_circle_blue)
                saturationPerscent.setTextColor(Color.parseColor("#2F80ED"))
                saturationText.text = "여유"
            }else if(placeSat>=30 && placeSat<70){
                saturationColor.setImageResource(R.drawable.outer_circle_yellow)
                saturationPerscent.setTextColor(Color.parseColor("#F2994A"))
                saturationText.text = "보통"
            }else{
                saturationColor.setImageResource(R.drawable.outer_circle_red)
                saturationPerscent.setTextColor(Color.parseColor("#EB5757"))
                saturationText.text = "꽉참"
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