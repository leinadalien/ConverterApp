package com.ldnprod.converter

import android.content.Context
import android.util.AttributeSet

class NumpadButton(context:Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatButton(context, attrs) {
    companion object {
        private val CONTAINER_POSITIONS = intArrayOf(R.attr.top_left, R.attr.top_right, R.attr.bottom_right,R.attr.bottom_left)

    }

    var container_position: ContainerPosition = ContainerPosition.DEFAULT
    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 1)
        if (container_position.ordinal > ContainerPosition.DEFAULT.ordinal){
            mergeDrawableStates(state, intArrayOf(CONTAINER_POSITIONS[container_position.ordinal]))
        }
        return state
    }
    enum class ContainerPosition {
        DEFAULT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT,
    }
}