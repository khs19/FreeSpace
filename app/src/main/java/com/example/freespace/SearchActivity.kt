package com.example.freespace
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.databinding.ActivitySearchBinding
import com.example.freespace.databinding.SearchRowBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: SearchAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }


    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        val query = rdb.orderByKey()
        val option =  FirebaseRecyclerOptions.Builder<Place>().setQuery(query, Place::class.java).build()
        adapter = SearchAdapter(option)
        adapter.itemClickListener = object : SearchAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                val pname = adapter.ViewHolder(SearchRowBinding.inflate(layoutInflater)).binding.searchPlace.toString()
                binding.apply {

                }
            }
        }
        binding.apply {
            searchRecyclerView.layoutManager = layoutManager
            searchRecyclerView.adapter = adapter
            searchBtn.setOnClickListener {
                val intent = Intent(it.context, SearchResultActivity::class.java)
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