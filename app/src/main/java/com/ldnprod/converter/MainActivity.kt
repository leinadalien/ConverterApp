package com.ldnprod.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.gridlayout.widget.GridLayout

class MainActivity : AppCompatActivity() {
    private var data = arrayOf("a", "b", "c", "d", "e")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gridlayout:GridLayout = findViewById(R.id.grid_layout)
        for (i in 1..8) {
            var button = layoutInflater.inflate(R.layout.num_button, gridlayout, false) as Button
            button.text = i.toString()

            gridlayout.addView(button)
        }



    }
}