package com.ldnprod.converter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldnprod.converter.ButtonAdapter
import com.ldnprod.converter.R

class NumpadFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.numpad_fragment, container, false) as RecyclerView
        view.layoutManager = GridLayoutManager(context, 3)
        view.adapter = ButtonAdapter()
        return view
    }
}