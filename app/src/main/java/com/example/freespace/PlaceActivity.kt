package com.example.freespace
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.MyPlaceAdapter
import com.example.freespace.databinding.ActivityPlaceBinding
import com.example.mysaturation.databinding.ActivityPlaceBinding
import com.google.firebase.database.DatabaseReference

class PlaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlaceBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPlaceAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun satInfo(percent : Int):String{
        var saturationinfo = "noinfo"
        if(percent<30)
            saturationinfo = "여유"
        else if(percent>=30 && percent<70)
            saturationinfo = "보통"
        else
            saturationinfo = "꽉참"
        return saturationinfo
    }

    fun init() {
        val intent = intent
        val placeSat = intent.getIntExtra("int", -1)
        val placeName = intent.getStringExtra("string")
        val placeInfo = intent.getStringExtra("string2")

        binding.apply {
            placename.text = placeName
            placesaturation.text = satInfo(placeSat)
        }
    }
}