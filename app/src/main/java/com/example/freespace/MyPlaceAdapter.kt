package com.example.freespace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freespace.databinding.SearchResultRowBinding

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyPlaceAdapter (options: FirebaseRecyclerOptions<Place>)
    : FirebaseRecyclerAdapter<Place, MyPlaceAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: SearchResultRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchResultRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Place) {
        val percent = model.pNum.toString().toDouble()/model.pMaxNum.toString().toDouble()
        holder.binding.apply {
            if(percent<0.3) {
                searchResultColor.setImageResource(R.drawable.fill_circle_blue)
                searchResultSaturation.text = "여유"
            } else if(percent>=0.3&&percent<0.7) {
                searchResultColor.setImageResource(R.drawable.fill_circle_yellow)
                searchResultSaturation.text = "보통"
            } else {
                searchResultColor.setImageResource(R.drawable.fill_circle_red)
                searchResultSaturation.text = "꽉참"
            }
            searchResultPlace.text = model.pName.toString()
        }
    }
}