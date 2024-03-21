package com.beniezsche.foodmenuassignment.models

data class FoodItem(val name: String,val image: String, val isNonVeg: Boolean, val rating: String, val prepTime: String, val prepGroup: String) {
    constructor() : this ("Italian Spaghetti Pasta", "https://images.io/2323", false, "4.5", "3 hours", "Medium")
}