package com.example.freespace
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.databinding.ActivitySearchResultBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SearchResultActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchResultBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPlaceAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        val intent = intent
        val placeName = intent.getStringExtra("placeName")

        val query = rdb.orderByChild("pname").startAt(placeName).endAt("$placeName\uf8ff")
        val option =  FirebaseRecyclerOptions.Builder<Place>().setQuery(query, Place::class.java).build()
        adapter = MyPlaceAdapter(option)
        adapter.itemClickListener = object : MyPlaceAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                val intent = Intent(this@SearchResultActivity, SaturationActivity::class.java)
                intent.putExtra("string", adapter.getItem(position).pName.toString())
                intent.putExtra("int", (adapter.getItem(position).pNum.toString().toDouble()/adapter.getItem(position).pMaxNum.toString().toDouble()*100).toInt())
                startActivity(intent)
            }
        }
        binding.apply {
            resultRecyclerView.layoutManager = layoutManager
            resultRecyclerView.adapter = adapter
            moveBookMark.setOnClickListener {
                val intent = Intent(this@SearchResultActivity, BookmarkActivity::class.java)
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