package com.ldnprod.converter

import android.content.Context
import android.util.AttributeSet

class NumpadButton(context:Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatButton(context, attrs) {
    companion object {
        private const val POSITION = R.attr.position
    }
    var position = POSITION
    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 4)
        mergeDrawableStates(state, intArrayOf(position))
        return state
    }
}