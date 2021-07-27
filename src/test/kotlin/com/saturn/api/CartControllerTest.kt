package com.saturn.api

import com.saturn.IntegrationTest
import com.saturn.domain.Cart
import com.saturn.domain.CartRepository
import com.saturn.domain.ProductRepository
import com.saturn.domain.RecipeRepository
import com.saturn.examples.ExampleProduct
import com.saturn.examples.ExampleRecipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.expectBody

@IntegrationTest
class CartControllerTest {

    @Autowired
    lateinit var client: SaturnClient

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var cartRepository: CartRepository

    val product1 = ExampleProduct().toDomain()
    val product2 = ExampleProduct().toDomain()
    val recipe1 = ExampleRecipe(products = listOf(product1, product2)).toDomain()
    val recipe2 = ExampleRecipe(products = listOf(product2)).toDomain()

    @Test
    fun `responds with 404 when when trying to get non exisitng cart`() {
        // given
        val nonExistingCartId = 100L

        // when
        val response = client.fetchCart(nonExistingCartId)

        // then
        response.expectStatus().isNotFound
    }

    @Test
    fun `returns an empty cart`() {
        // given
        val cart = Cart.empty()
        cartRepository.save(cart)

        // when
        val response = client.fetchCart(cart.id!!)

        // then
        response
            .expectStatus().isOk
            .expectBody<CartsController.Cart>()
            .isEqualTo(CartsController.Cart(cart.id!!, 0, emptyList()))
    }

    @Test
    fun `adds recipes to cart`() {
        // given
        recipesExist()
        val cart = Cart.empty()
        cartRepository.save(cart)

        // when
        val addRecipe1Response = client.addRecipeToCart(cart.id!!, recipe1.id!!)
        val addRecipe2Response = client.addRecipeToCart(cart.id!!, recipe2.id!!)
        val fetchCartResponse = client.fetchCart(cart.id!!)

        // then
        addRecipe1Response.expectStatus().isOk
        addRecipe2Response.expectStatus().isOk
        fetchCartResponse
            .expectStatus().isOk
            .expectBody<CartsController.Cart>()
            .consumeWith {
                val fetchedCart = it.responseBody!!
                assertThat(fetchedCart.totalInCents).isEqualTo(product1.priceInCents!! + product2.priceInCents!! + product2.priceInCents!!)
                assertThat(fetchedCart.items.map { it.product.id }).contains(product1.id, product2.id, product2.id)
            }
    }

    @Test
    fun `removes recipes from cart`() {
        // given
        recipesExist()
        val cart = Cart.empty()
        cartRepository.save(cart)

        // when
        client.addRecipeToCart(cart.id!!, recipe1.id!!)
        client.addRecipeToCart(cart.id!!, recipe2.id!!)
        val deleteRecipeFromCart = client.deleteRecipeFromCart(cart.id!!, recipe1.id!!)
        val fetchCartResponse = client.fetchCart(cart.id!!)

        // then
        deleteRecipeFromCart.expectStatus().isOk
        fetchCartResponse
            .expectStatus().isOk
            .expectBody<CartsController.Cart>()
            .consumeWith {
                val fetchedCart = it.responseBody!!
                assertThat(fetchedCart.totalInCents).isEqualTo(product2.priceInCents!!)
                assertThat(fetchedCart.items.map { it.product.id }).contains(product2.id)
            }
    }

    @Test
    fun `responds with 404 error when trying to add a recipe to non existing cart`() {
        // given
        recipesExist()
        val invalidCartId = 900L

        // expect
        client.addRecipeToCart(invalidCartId, recipe1.id!!)
            .expectStatus().isNotFound
    }

    @Test
    fun `responds with 404 error when trying to add a non exisitng recipe to a cart`() {
        // given
        val cart = Cart.empty()
        cartRepository.save(cart)
        val invalidRecipeId = 900L

        // expect
        client.addRecipeToCart(cart.id!!, invalidRecipeId)
            .expectStatus().isNotFound
    }

    @Test
    fun `responds with 404 error when trying to delete a recipe from non existing cart`() {
        // given
        recipesExist()
        val invalidCartId = 900L

        // expect
        client.deleteRecipeFromCart(invalidCartId, recipe1.id!!)
            .expectStatus().isNotFound
    }

    @Test
    fun `responds with 404 error when trying to delete a non existing recipe from a cart`() {
        // given
        val cart = Cart.empty()
        cartRepository.save(cart)
        val invalidRecipeId = 900L

        // expect
        client.deleteRecipeFromCart(cart.id!!, invalidRecipeId)
            .expectStatus().isNotFound
    }

    @Test
    fun `does nothing when trying to remove a recipe that does not exist in the cart`() {
        // given
        recipesExist()
        val cart = Cart.empty()
        cartRepository.save(cart)

        // expect
        client.deleteRecipeFromCart(cart.id!!, recipe1.id!!)
            .expectStatus().isOk
    }

    private fun recipesExist() {
        productRepository.save(product1)
        productRepository.save(product2)
        recipeRepository.save(recipe1)
        recipeRepository.save(recipe2)
    }
}