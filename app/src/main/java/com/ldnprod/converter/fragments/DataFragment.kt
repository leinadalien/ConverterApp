package com.ldnprod.converter.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldnprod.converter.Category
import com.ldnprod.converter.CategoryUnit
import com.ldnprod.converter.R

class DataFragment: Fragment(R.layout.data_fragment) {

    var spinnerData = ArrayList<String>()
    var categories = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesInit()
        var view = inflater.inflate(R.layout.data_fragment, null)
        val spinnerAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, spinnerData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(view.findViewById<Spinner>(R.id.spinner)) {
            adapter = spinnerAdapter
            prompt = "Category"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {
                    val toast = Toast.makeText(
                        context,
                        "Selected", Toast.LENGTH_SHORT
                    )
                    toast.show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        view.findViewById<EditText>(R.id.from_edit_text).inputType = InputType.TYPE_NULL
        view.findViewById<EditText>(R.id.to_edit_text).inputType = InputType.TYPE_NULL
        return view
    }
    private fun categoriesInit(){
        with(Category("weight")) {
            addUnit(CategoryUnit("kg", 1.0))
            addUnit(CategoryUnit("g", 1000.0))
            addUnit(CategoryUnit("mg", 1000000.0))
            addUnit(CategoryUnit("q", 0.01))
            addUnit(CategoryUnit("t", 0.001))
            addUnit(CategoryUnit("ct", 5000.0))
            addUnit(CategoryUnit("lb", 2.2046226218488))
            categories.add(this)
            spinnerData.add(name)
        }
        with(Category("length")) {
            addUnit(CategoryUnit("m", 1.0))
            addUnit(CategoryUnit("km", 0.001))
            addUnit(CategoryUnit("mile", 0.00062137119223734))
            addUnit(CategoryUnit("in", 39.3701))
            addUnit(CategoryUnit("ft", 3.28084))
            addUnit(CategoryUnit("yard", 1.0936132983378))
            categories.add(this)
            spinnerData.add(name)
        }
        with(Category("volume")) {
            addUnit(CategoryUnit("m3", 1.0))
            addUnit(CategoryUnit("l", 1000.0))
            addUnit(CategoryUnit("gal", 0.00062137119223734))
            addUnit(CategoryUnit("oz", 33814.99999))
            addUnit(CategoryUnit("pt", 2113.38))
            addUnit(CategoryUnit("qt", 1056.69))
            addUnit(CategoryUnit("gal", 264.172))
            categories.add(this)
            spinnerData.add(name)
        }
    }

}