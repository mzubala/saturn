package com.saturn.examples

import com.saturn.domain.Product
import com.saturn.domain.Recipe

data class ExampleRecipe(
    val name: String = "test",
    val products: List<Product> = listOf(
        ExampleProduct().toDomain(),
        ExampleProduct().toDomain()
    )
) {
    fun toDomain(): Recipe {
        val recipe = Recipe()
        recipe.name = name
        recipe.products.addAll(products)
        return recipe
    }
}