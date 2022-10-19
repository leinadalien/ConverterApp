package com.ldnprod.converter

import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.gridlayout.widget.GridLayout

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()

    }

    private fun initButtons(){
        val gridlayout:GridLayout = findViewById(R.id.grid_layout)
        var button:Button
        for (i in 1..9) {
            button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
            button.text = i.toString()
            gridlayout.addView(button)
        }
        button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
        button.text = "0"
        gridlayout.addView(button)
        button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
        button.text = "."
        gridlayout.addView(button)
        button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
        button.text = "*"
        gridlayout.addView(button)
    }
}