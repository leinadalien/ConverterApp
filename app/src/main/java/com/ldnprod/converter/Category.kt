package com.ldnprod.converter

import java.math.BigDecimal

class Category(val name:String) {

    var units = ArrayList<CategoryUnit>()
    fun addUnit(unit: CategoryUnit) {
        if (units.contains(unit)) {
            throw IllegalArgumentException("${unit.measurement} already exists in $name")
        }
        units.add(unit)
    }
    fun convert(fromUnit: CategoryUnit, toUnit: CategoryUnit, value: BigDecimal): BigDecimal {
        if (units.contains(fromUnit) && units.contains(toUnit)){
            if (fromUnit.factor == toUnit.factor) return value
            return value/(fromUnit.factor).toBigDecimal() * (toUnit.factor).toBigDecimal()
        }
        else {
            throw IllegalArgumentException("$name doesn't contain such units as ${fromUnit.measurement} and ${toUnit.measurement}")
        }
    }
    fun getMeasurements() = units.map { it.measurement }


}