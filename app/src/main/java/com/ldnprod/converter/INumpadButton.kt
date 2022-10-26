package com.ldnprod.converter

import androidx.appcompat.widget.AppCompatButton

interface INumpadButton {
    var containerPosition: ContainerPositions
}
enum class ContainerPositions(val resource: Int){
    DEFAULT(0),
    TOP_LEFT(R.attr.top_left),
    TOP_RIGHT(R.attr.top_right),
    BOTTOM_RIGHT(R.attr.bottom_right),
    BOTTOM_LEFT(R.attr.bottom_left)

}