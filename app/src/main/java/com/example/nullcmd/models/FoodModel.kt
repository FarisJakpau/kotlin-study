package com.example.nullcmd.models

data class FoodModel(
    val strMeal: String? = null,
    val strCategory: String? = null,
    val strMealThumb: String? = null,
    val idMeal: String? = null,
    var isSelected: Boolean = false,
    val strArea: String? = null,
    val strYoutube: String? = null
)