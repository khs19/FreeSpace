package com.example.freespace
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.databinding.ActivityBookmarkBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BookmarkActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookmarkBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPlaceAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        val query = rdb.orderByChild("bookmark").equalTo(true.toString().toBoolean())
        val option = FirebaseRecyclerOptions.Builder<Place>().setQuery(query, Place::class.java).build()
        adapter = MyPlaceAdapter(option)
        adapter.itemClickListener = object:MyPlaceAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) {
                val intent = Intent(this@BookmarkActivity, PlaceActivity::class.java)
                intent.putExtra("placeName", adapter.getItem(position).pName.toString())
                intent.putExtra("placeInfo", adapter.getItem(position).pInfo.toString())
                intent.putExtra("bookmark", adapter.getItem(position).bookmark.toString().toBoolean())
                startActivity(intent)
            }
        }
        binding.apply {
            bookMarkRecyclerView.layoutManager = layoutManager
            bookMarkRecyclerView.adapter = adapter
            moveBookMark.setOnClickListener {
                val intent = Intent(this@BookmarkActivity, BookmarkSettingActivity::class.java)
                startActivity(intent)
            }
            moveHome.setOnClickListener {
                val intent = Intent(this@BookmarkActivity, IntroActivity::class.java)
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