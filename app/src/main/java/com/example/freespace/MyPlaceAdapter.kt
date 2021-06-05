package com.example.freespace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyPlaceAdapter (options: FirebaseRecyclerOptions<Place>)
    : FirebaseRecyclerAdapter<Place, MyPlaceAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    fun satInfo(mNum : Double, num : Double) : String{
        var n = num/mNum
        var saturationinfo = "noinfo"
        if(n<0.3)
            saturationinfo = "여유"
        else if(n>=0.3 && n<0.7)
            saturationinfo = "보통"
        else
            saturationinfo = "꽉참"
        return saturationinfo
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Place) {
        holder.binding.apply {
            placename.text = model.pName.toString()
            placesaturation.text = satInfo(model.pMaxNum.toString().toDouble(), model.pNum.toString().toDouble())
        }
    }
}