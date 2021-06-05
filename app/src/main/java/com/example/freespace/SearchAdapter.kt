package com.example.freespace

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freespace.databinding.SearchResultRowBinding
import com.example.freespace.databinding.SearchRowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter (val items:ArrayList<String>)
    : RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder:SearchAdapter.ViewHolder, view: View, data:String, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val pname : TextView = itemView.findViewById(R.id.searchPlace)
        init{
            pname.setOnClickListener {
                itemClickListener?.OnItemClick(this,it,items[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_row,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pname.text = items[position]
    }
    override fun getItemCount(): Int { //어댑터네 오버라이드
        return items.size
    }
}