package com.saturn.api

import com.saturn.domain.RecipeRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recipes")
class RecipesController(
    private val recipeRepository: RecipeRepository
) {
    @GetMapping
    fun getRecipes(): List<Recipe> = recipeRepository.findAll().map {
        Recipe(
            it.id!!,
            it.name!!,
            it.products.map {
                Product(
                    it.id!!,
                    it.name!!,
                    it.priceInCents!!
                )
            }
        )
    }

    data class Recipe(val id: Long, val name: String, val products: List<Product>)

    data class Product(val id: Long, val name: String, val priceInCents: Long)
}