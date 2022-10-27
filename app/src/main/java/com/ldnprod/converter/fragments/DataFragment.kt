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
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class DataFragment: Fragment(R.layout.data_fragment) {

    var spinnerData = ArrayList<String>()
    var categories = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesInit()
        val view = inflater.inflate(R.layout.data_fragment, null)
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
        val parser: XmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(activity?.resources?.assets?.open("categories_information.xml"), null)
        var category: Category? = null
        var unitMeasurement: String? = null
        var unitFactor: Double? = null
        var content: String? = null
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT){
            val tagName = parser.name
            when(eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName.equals("category", true)) {
                        val categoryName = parser.getAttributeValue(null, "name")
                        category = Category(categoryName)
                        spinnerData.add(categoryName)
                    }
                }
                XmlPullParser.TEXT -> content = parser.text
                XmlPullParser.END_TAG -> {

                    if (tagName.equals("measurement",true)) {
                        unitMeasurement = content
                    }
                    if (tagName.equals("factor",true)) {
                        unitFactor = content?.toDouble()
                    }
                    if (tagName.equals("unit",true)) {
                        category?.addUnit(CategoryUnit(unitMeasurement!!,unitFactor!!))
                    }
                    if (tagName.equals("category",true)) {
                        categories.add(category!!)
                    }
                }
            }
            eventType = parser.next()
        }
    }

}