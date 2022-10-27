package com.ldnprod.converter

class Category(public val name:String) {

    var units = HashSet<CategoryUnit>()
    public fun addUnit(unit: CategoryUnit) {
        if (units.contains(unit)) {
            throw IllegalArgumentException("${unit.measurement} already exists in $name")
        }
        units.add(unit)
    }
    public fun convertTo(fromUnit: CategoryUnit, toUnit: CategoryUnit, value: Double): Double {
        if (units.contains(fromUnit) && units.contains(toUnit)){
            return value/fromUnit.factor * toUnit.factor
        }
        else {
            throw IllegalArgumentException("$name doesn't contain such units as ${fromUnit.measurement} and ${toUnit.measurement}")
        }
    }


}