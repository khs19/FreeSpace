package com.example.freespace
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.databinding.ActivityPlaceBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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

    fun init() {
        val intent = intent
        val placeSat = intent.getIntExtra("int", -1)
        val placeName = intent.getStringExtra("placeName")
        val placeInfo = intent.getStringExtra("placeInfo")
        val bookmark = intent.getBooleanExtra("bookmark", false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        binding.apply {
            if(bookmark)
                placeBookMarkCheck.isChecked = true
            else
                placeBookMarkCheck.isChecked = false


            placeBookMarkCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    rdb.child(placeName.toString()).child("bookmark").setValue(true.toString().toBoolean())
                    rdb.child(placeName.toString()).child("bookmarkSetting").setValue(true.toString().toBoolean())
                }else{
                    rdb.child(placeName.toString()).child("bookmark").setValue(false.toString().toBoolean())
                    rdb.child(placeName.toString()).child("bookmarkSetting").setValue(false.toString().toBoolean())
                }
            }
            if(placeSat<30){
                placeLinear.setBackgroundColor(Color.parseColor("#2F80ED"))
                placeSaturation.text = "여유"
            }else if(placeSat>=30 && placeSat<70){
                placeLinear.setBackgroundColor(Color.parseColor("#F2994A"))
                placeSaturation.text = "보통"
            }else{
                placeLinear.setBackgroundColor(Color.parseColor("#EB5757"))
                placeSaturation.text = "꽉참"
            }
            placePname.text = placeName
            placeDetail.text = placeInfo
        }
    }
}