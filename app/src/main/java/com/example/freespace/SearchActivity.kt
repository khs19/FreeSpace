package com.example.freespace
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.databinding.ActivitySearchBinding
import com.google.firebase.database.DatabaseReference
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: SearchAdapter
    var recents = mutableListOf<String>()
    var array = ArrayList<String>()
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        initData()
        binding.apply {
            searchRecyclerView.layoutManager = LinearLayoutManager(parent,LinearLayoutManager.VERTICAL,false)
//            var arrRecent = ArrayList<String>()
//            recent(arrRecent)
            adapter = SearchAdapter(array)
            searchBtn.setOnClickListener {
                writefile(searchEdit.text.toString())
                val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
                intent.putExtra("placeName", searchEdit.text.toString())
                startActivity(intent)
            }
            adapter.itemClickListener = object : SearchAdapter.OnItemClickListener{
                override fun OnItemClick(holder: SearchAdapter.ViewHolder, view: View, data: String, position: Int) {
                    searchEdit.setText(array[position])
                }
            }
            searchRecyclerView.adapter = adapter
        }
    }
    fun readFileScan(scan: Scanner){
        while(scan.hasNextLine()){
            val name = scan.nextLine()
            if(!array.contains(name)) {
                array.add(name)
            }
        }
        scan.close()
    }
    private fun initData() {

        try { // addvoc를 하지 않아 out.txt가 생성되지 않았을경우 try catch
            val scan2 = Scanner(this.openFileInput("recent.txt"))
            readFileScan(scan2) // addvocactivity 에서 삽입한 텍스트를 삽입
        }catch(e: Exception){ }
    }
    fun recent(items: ArrayList<String>){
        items.clear()
        var i = 0
        while(i<10&&i<array.size){
            items.add(array[i])
        }
    }
    fun writefile(name: String){
        val output= PrintStream(this.openFileOutput("recent.txt", MODE_APPEND))
        output.println(name)
        output.close()
    }
}