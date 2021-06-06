package com.example.freespace

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init(){
        val add = findViewById<TextView>(R.id.textView2)
        add.setOnClickListener {
//            var rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
//            val item = Place("스타벅스 건대입구", 30, 24, "스타벅스건대입구 정보", false, false)
//            val item2 = Place("투썸플레이스 어린이대공원", 50, 26, "투썸플레이스 어린이대공원 정보", false, false)
//            val item3 = Place("카페 온더플랜", 60, 30, "카페 온더플랜 정보", false, false)
//            val item4 = Place("탑앤탐스 구의", 30, 29, "탑앤탐스 구의 정보", false, false)
//            val item5 = Place("할리스커피 일감호", 25, 1, "할리스커피 일감호 정보", false, false)
//            val item6 = Place("스타벅스 스타시티", 20, 10, "스타벅스 스타시티 정보", false, false)
//            rdb.child("스타벅스 건대입구").setValue(item)
//            rdb.child("투썸플레이스 어린이대공원").setValue(item2)
//            rdb.child("카페 온더플랜").setValue(item3)
//            rdb.child("탑앤탐스 구의").setValue(item4)
//            rdb.child("할리스커피 일감호").setValue(item5)
//            rdb.child("스타벅스 스타시티").setValue(item6)
        }
    }
}