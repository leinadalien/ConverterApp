package com.ldnprod.converter.fragments

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.ldnprod.converter.Category
import com.ldnprod.converter.CategoryUnit
import com.ldnprod.converter.DataViewModel
import com.ldnprod.converter.R
import com.ldnprod.converter.databinding.DataFragmentBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class DataFragment: Fragment(R.layout.data_fragment) {
    private val dataViewModel: DataViewModel by activityViewModels()
    private var spinnerData = ArrayList<String>()
    private lateinit var binding: DataFragmentBinding
    private var categories = ArrayList<Category>()
    private val LOG_TAG = "DataFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataFragmentBinding.inflate(inflater)
        categoriesInit()
        val spinnerAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, spinnerData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.spinner) {
            adapter = spinnerAdapter
            prompt = "Category"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {
                    changeCategory(categories[selectedItemPosition])
                    Log.i(LOG_TAG, "selected $selectedItem")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        binding.fromEditText.showSoftInputOnFocus = false
        binding.toEditText.showSoftInputOnFocus = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataViewModel.textFrom.observe(activity as LifecycleOwner) {
            binding.fromEditText.setText(it)
        }
        dataViewModel.textTo.observe(activity as LifecycleOwner) {
            binding.toEditText.setText(it)
        }
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
    private fun changeCategory(category: Category){
        setSpinnerData(binding.fromSpinner,  category.getMeasurements())
        setSpinnerData(binding.toSpinner,  category.getMeasurements())
    }
    private fun setSpinnerData(spinner: Spinner, data:List<String>){
        val spinnerAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, data)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner){
            adapter = spinnerAdapter
            prompt = "Category unit"
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {

                    Log.i(LOG_TAG, "${spinner.id} selected $selectedItem")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

}