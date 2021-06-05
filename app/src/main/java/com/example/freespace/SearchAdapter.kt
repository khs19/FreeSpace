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

class SearchAdapter (options: FirebaseRecyclerOptions<Place>)
    : FirebaseRecyclerAdapter<Place, SearchAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null
    inner class ViewHolder(var binding: SearchRowBinding): RecyclerView.ViewHolder(binding.root){
        val pname : String = binding.searchPlace.toString()
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Place) {
        holder.binding.apply {
            searchPlace.text = model.pName.toString()
        }
    }
}