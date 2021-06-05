package com.example.freespace
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.freespace.databinding.FragmentImageSlideBinding


class ImageSlideFragment(val image : Int) : Fragment() {
    var binding: FragmentImageSlideBinding ?=null
    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedINstanceState: Bundle?):View?{
        binding = FragmentImageSlideBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            slideImage.setImageResource(image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}