package com.ldnprod.converter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ButtonAdapter: RecyclerView.Adapter<ButtonAdapter.ButtonHolder>() {

    private val buttons = ('1').rangeTo('9').toList() + '0' + '.' + 'x'

    class ButtonHolder(view:View): RecyclerView.ViewHolder(view) {
        val button = view.findViewById<NumpadButton>(R.id.numpad_button)
    }

    override fun getItemCount() = buttons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.numpad_button, parent, false)

        return ButtonHolder(layout)
    }

    override fun onBindViewHolder(holder: ButtonHolder, position: Int) {
        val item = buttons[position]
        with(holder.button){
            if (position == 0) {
                container_position = NumpadButton.ContainerPosition.BOTTOM_LEFT
            }
            holder.button.text = item.toString()
            setOnClickListener {
                Toast.makeText(context, "clicked $item", Toast.LENGTH_SHORT).show()
            }
        }
    }
}