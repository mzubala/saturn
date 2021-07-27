package com.saturn.api

import com.saturn.domain.CartRepository
import com.saturn.domain.CartService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/carts")
class CartsController(
    private val cartRepository: CartRepository,
    private val cartService: CartService
) {

    @GetMapping("/{cartId}")
    fun getCart(@PathVariable cartId: Long): Cart =
        cartRepository.findById(cartId).let {
            Cart(
                id = it.id!!,
                totalInCents = it.totalInCents!!,
                items = it.items.map {
                    CartItem(
                        it.id!!,
                        it.product!!.priceInCents!!,
                        it.product!!.let {
                            Product(
                                it.id!!,
                                it.name!!
                            )
                        }
                    )
                }
            )
        }

    @PostMapping("/{cartId}/add_recipe")
    fun addRecipeToCart(@PathVariable cartId: Long, @RequestBody request: AddRecipeRequest) {
        cartService.addRecipeToCart(cartId, request.receipeId)
    }

    @DeleteMapping("/{cartId}/delete_recipe/{recipeId}")
    fun deleteRecipeFromCart(@PathVariable cartId: Long, @PathVariable recipeId: Long) {
        cartService.removeRecipeFromCart(cartId, recipeId)
    }

    data class AddRecipeRequest(val receipeId: Long)

    data class Cart(
        val id: Long,
        val totalInCents: Long,
        val items: List<CartItem>
    )

    data class CartItem(
        val id: Long,
        val totalInCents: Long,
        val product: Product
    )

    data class Product(
        val id: Long,
        val name: String
    )
}