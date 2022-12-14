package com.ldnprod.converter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldnprod.converter.ButtonAdapter
import com.ldnprod.converter.DataViewModel
import com.ldnprod.converter.R

class NumpadFragment: Fragment(R.layout.numpad_fragment) {
    private val dataViewModel: DataViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.numpad_fragment, container, false) as RecyclerView
        view.layoutManager = object: GridLayoutManager(context, 3){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        view.adapter = activity?.assets?.let { ButtonAdapter(it.open("numpad.xml"), dataViewModel, activity as LifecycleOwner) }
        return view
    }
}
