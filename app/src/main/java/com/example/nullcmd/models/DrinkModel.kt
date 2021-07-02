package com.example.nullcmd.models

data class DrinkModel(
    val strDrink: String? = null,
    val strCategory: String? = null,
    val strDrinkThumb: String? = null,
    val idDrink: String? = null,
    var isSelected: Boolean = false,
    val strGlass: String? = null
)