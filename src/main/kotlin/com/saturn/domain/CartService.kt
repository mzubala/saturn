package com.saturn.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CartService(
    private val cartRepository: CartRepository,
    private val recipeRepository: RecipeRepository
) {

    @Transactional
    fun addRecipeToCart(cartId: Long, recipeId: Long) {
        val recipe = recipeRepository.findById(recipeId)
        val cart = cartRepository.findById(cartId)
        recipe.products.forEach {
            cart.addProduct(it)
        }
    }

    @Transactional
    fun removeRecipeFromCart(cartId: Long, recipeId: Long) {
        val recipe = recipeRepository.findById(recipeId)
        val cart = cartRepository.findById(cartId)
        recipe.products.forEach {
            cart.removeProduct(it)
        }
    }
}