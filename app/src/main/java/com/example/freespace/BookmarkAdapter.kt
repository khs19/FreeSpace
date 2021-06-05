package com.example.freespace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freespace.databinding.BookmarkSettingRowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class BookmarkAdapter (options: FirebaseRecyclerOptions<Place>)
    : FirebaseRecyclerAdapter<Place, BookmarkAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: BookmarkSettingRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = BookmarkSettingRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }





    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int, model: Place) {
        val percent = model.pNum.toString().toDouble()/model.pMaxNum.toString().toDouble()
        holder.binding.apply {
            if(model.bookmarkSetting)
                bookMarkCheck.isChecked = true
            else
                bookMarkCheck.isChecked = false
            bookMarkPlace.text = model.pName.toString()
        }
    }
}