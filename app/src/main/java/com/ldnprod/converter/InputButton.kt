package com.ldnprod.converter

import android.content.Context
import android.util.AttributeSet

class InputButton(context:Context, attrs: AttributeSet):
    androidx.appcompat.widget.AppCompatButton(context, attrs), INumpadButton {
    private var ctrPosition = ContainerPositions.DEFAULT
    override var containerPosition: ContainerPositions
        get() = ctrPosition
        set(value) {
            ctrPosition = value
        }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 1)
        if(ctrPosition == null){
            ctrPosition = ContainerPositions.DEFAULT
        }
        if (ctrPosition != ContainerPositions.DEFAULT){
            mergeDrawableStates(state, intArrayOf(ctrPosition.resource))
        }
        return state
    }

}