package com.ldnprod.converter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ButtonAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val INPUT_BUTTON_TYPE = 1
        const val FUN_BUTTON_TYPE = 2
    }
    private val buttons = ('1').rangeTo('9').toList() + '0' + '.' + 'x'

    class InputButtonHolder(view:View): RecyclerView.ViewHolder(view) {
        val button: InputButton = view.findViewById(R.id.numpad_button)
    }
    class FunButtonHolder(view:View): RecyclerView.ViewHolder(view) {
        val button: FunctionButton = view.findViewById(R.id.numpad_button)
    }

    override fun getItemCount() = buttons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == FUN_BUTTON_TYPE){
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.function_button, parent, false)
            return FunButtonHolder(layout)
        } else {
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.input_button, parent, false)
            return InputButtonHolder(layout)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1) {
            return FUN_BUTTON_TYPE
        }
        return INPUT_BUTTON_TYPE
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = buttons[position]
        when(holder.itemViewType){
            INPUT_BUTTON_TYPE -> {
                with((holder as InputButtonHolder).button) {
                    containerPosition = when(position){
                        0 -> ContainerPositions.TOP_LEFT
                        2 -> ContainerPositions.TOP_RIGHT
                        itemCount - 1 -> ContainerPositions.BOTTOM_RIGHT
                        itemCount - 3 -> ContainerPositions.BOTTOM_LEFT
                        else -> ContainerPositions.DEFAULT
                    }
                    text = item.toString()
                    setOnClickListener {
                        Toast.makeText(context, "clicked $item", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> {
                with((holder as FunButtonHolder).button){
                    containerPosition = when(position){
                        0 -> ContainerPositions.TOP_LEFT
                        2 -> ContainerPositions.TOP_RIGHT
                        itemCount - 1 -> ContainerPositions.BOTTOM_RIGHT
                        itemCount - 3 -> ContainerPositions.BOTTOM_LEFT
                        else -> ContainerPositions.DEFAULT
                    }
                }
            }
        }

    }
}