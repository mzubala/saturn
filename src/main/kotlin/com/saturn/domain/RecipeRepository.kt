package com.saturn.domain

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.Repository

interface RecipeRepository: Repository<Recipe, Long> {

    @EntityGraph(Recipe.WITH_PRODUCTS)
    fun findAll(): List<Recipe>

    fun save(recipe: Recipe)

    @EntityGraph(Recipe.WITH_PRODUCTS)
    fun findById(recipeId: Long): Recipe
}
