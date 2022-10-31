package com.ldnprod.converter.fragments

import  android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
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
import java.text.DecimalFormat

class DataFragment: Fragment(R.layout.data_fragment) {
    private val dataViewModel: DataViewModel by activityViewModels()
    private var spinnerData = ArrayList<String>()
    private lateinit var binding: DataFragmentBinding
    private var categories = ArrayList<Category>()
    private val LOG_TAG = "DataFragment"
    private lateinit var currentCategory: Category
    private lateinit var firstUnit: CategoryUnit
    private  lateinit var secondUnit: CategoryUnit
    private var focusedEditText: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataFragmentBinding.inflate(inflater)
        categoriesInit()
        currentCategory = categories[0]
        firstUnit = currentCategory.units[0]
        secondUnit = currentCategory.units[0]
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
        binding.fromEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus -> focusedEditText = if (hasFocus) binding.fromEditText else null}
        binding.toEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus -> focusedEditText = if (hasFocus) binding.toEditText else null}
        binding.toEditText.showSoftInputOnFocus = false
        focusedEditText = binding.fromEditText
        return binding.root
    }
    private fun updateConverting(fromEditText: EditText = , toEditText: EditText,
                                   fromUnit: CategoryUnit = firstUnit,
                                   toUnit: CategoryUnit = secondUnit, data: String) {
        fromEditText.setText(data)
        if (fromEditText.text.isNotEmpty()) {
            toEditText.setText(
                DecimalFormat("#.####").format(
                    currentCategory.convertTo(
                        fromUnit,
                        toUnit,
                        data.toDouble())
                ).toString()
            )
        } else toEditText.setText("")
    }
    private fun getInput(input: String){
        focusedEditText?.let {
            var start = it.selectionStart
            var end = it.selectionEnd
            try {
                input.toDouble()
                var allIsGood = true
                if (input == "0") {
                    if (start == 0 && end == 0 && it.text.isNotEmpty()) allIsGood = false
                    if (start == 0 && end < it.text.length - 1 && (it.text[end + 1] == '.' || it.text[end + 1] == '0')) allIsGood = false
                    if (start == 0 && end != it.text.length && it.text.subSequence(start, end).contains('.')) allIsGood = false
                    if (start == 1 && it.text[0] == '0') allIsGood = false
                }
                if (allIsGood) it.text.replace(start, end, input)
            } catch (e: NumberFormatException) {
                if (input.equals(".")){
                    if (!it.text.subSequence(0, start).contains(input) &&
                        !it.text.subSequence(end, it.text.length).contains(input)) {
                        if (start == 0) {
                            it.text.replace(start, end, "0 + $input")
                        } else it.text.replace(start, end, input)
                        dataViewModel.blockedButtons.value?.add(Pair("input","."))
                    }
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataViewModel.passingInput.observe(activity as LifecycleOwner) { input ->
            getInput(input)
        }
        dataViewModel.passingFunction.observe(activity as LifecycleOwner) { action ->
            focusedEditText?.let {
                if (action == "delete") {
                    var start = it.selectionStart
                    var end = it.selectionEnd
                    if (end == start && start > 0) {
                        start -= 1
                    }
                    it.text.delete(start, end)
                }
            }
        }
        binding.fromEditText.requestFocus()
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
        currentCategory =  category
        setSpinnerData(binding.fromSpinner,  category.getMeasurements())
        setSpinnerData(binding.toSpinner,  category.getMeasurements())
        focusedEditText = binding.fromEditText
        binding.fromEditText.requestFocus()
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
                    when(spinner.id) {
                        R.id.from_spinner -> firstUnit = currentCategory.units[selectedItemPosition]
                        R.id.to_spinner -> secondUnit = currentCategory.units[selectedItemPosition]
                    }
                    Log.i(LOG_TAG, "${spinner.id} selected $selectedItem")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

}