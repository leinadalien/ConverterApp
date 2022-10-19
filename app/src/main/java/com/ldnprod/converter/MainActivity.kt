package com.ldnprod.converter

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout


class MainActivity : AppCompatActivity() {
    var spinnerData = arrayListOf("One", "Two", "Three")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnerInit()
        buttonsInit()

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
    private fun buttonsInit(){
        val btnTexts = ArrayList<String>()
        for (i in 1..9){
            btnTexts.add(i.toString())
        }
        btnTexts.add("0")
        btnTexts.add(".")
        btnTexts.add("*")
        val gridlayout:GridLayout = findViewById(R.id.grid_layout)
        var button:Button
        for (text in btnTexts){
            button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
            button.text = text
            gridlayout.addView(button)
        }
//        for (i in 1..9) {
//            button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
//            button.text = i.toString()
//            gridlayout.addView(button)
//        }
//        button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
//        button.text = "0"
//        gridlayout.addView(button)
//        button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
//        button.text = "."
//        gridlayout.addView(button)
//        button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
//        button.text = "*"
//        gridlayout.addView(button)
    }
}