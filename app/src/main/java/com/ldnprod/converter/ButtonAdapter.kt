package com.ldnprod.converter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class ButtonAdapter(private val stream: InputStream): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val INPUT_BUTTON_TYPE = 1
        const val FUN_BUTTON_TYPE = 2
    }
    //private val buttons = ('1').rangeTo('9').toList() + '0' + '.' + 'x'
    private val buttons = ArrayList<Pair<String, String>>()
    class InputButtonHolder(view:View): RecyclerView.ViewHolder(view) {
        val button: InputButton = view.findViewById(R.id.numpad_button)
    }
    class FunButtonHolder(view:View): RecyclerView.ViewHolder(view) {
        val button: FunctionButton = view.findViewById(R.id.numpad_button)
    }
    init {
        getButtons()
    }
    private fun getButtons() {
        val parser:XmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(stream, null)
        var buttonType: String? = null
        var buttonContent: String? = null
        var eventType = parser.eventType
        while(eventType != XmlPullParser.END_DOCUMENT){
            val tagName = parser.name
            when(eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName.equals("button", true)) {
                        buttonType = parser.getAttributeValue(null, "type")
                    }
                }
                XmlPullParser.TEXT -> buttonContent = parser.text
                XmlPullParser.END_TAG -> {
                    if (tagName.equals("button", true)){
                        buttons.add(Pair(buttonType!!,buttonContent!!))
                    }
                }
            }
            eventType = parser.next()
        }
    }
    override fun getItemCount() = buttons.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FUN_BUTTON_TYPE){
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.function_button, parent, false)
            FunButtonHolder(layout)
        } else {
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.input_button, parent, false)
            InputButtonHolder(layout)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(buttons[position].first) {
            "function" -> FUN_BUTTON_TYPE
            "input" -> INPUT_BUTTON_TYPE
            else -> FUN_BUTTON_TYPE
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = buttons[position]
        when(getItemViewType(position)){
            INPUT_BUTTON_TYPE -> {
                with((holder as InputButtonHolder).button) {
                    containerPosition = when(position){
                        0 -> ContainerPositions.TOP_LEFT
                        2 -> ContainerPositions.TOP_RIGHT
                        itemCount - 1 -> ContainerPositions.BOTTOM_RIGHT
                        itemCount - 3 -> ContainerPositions.BOTTOM_LEFT
                        else -> ContainerPositions.DEFAULT
                    }
                    text = item.second
                    setOnClickListener {
                        Toast.makeText(context, "clicked ${item.second}", Toast.LENGTH_SHORT).show()
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
                    when(item.second){
                        "delete" -> setImageResource(android.R.drawable.ic_input_delete)
                    }
                }
            }
        }

    }
}