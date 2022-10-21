package com.ldnprod.converter

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout


class MainActivity : AppCompatActivity() {
    var spinnerData = ArrayList<String>()
    var categories = ArrayList<Category>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        categoriesInit()
        spinnerInit()

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
    private fun spinnerInit(){
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(findViewById<Spinner>(R.id.spinner)){
            adapter = spinnerAdapter
            prompt = "Category"
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Selected", Toast.LENGTH_SHORT
                    )
                    toast.show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}