package com.saturn.domain

import com.saturn.examples.ExampleProduct
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartTest {

    @Test
    fun `empty cart has zero total`() {
        // given
        val emptyCart = Cart.empty()

        // expect
        assertThat(emptyCart.totalInCents).isEqualTo(0)
    }

    @Test
    fun `adds products to cart`() {
        // given
        val cart = Cart.empty()
        val product1 = ExampleProduct().toDomain()
        val product2 = ExampleProduct().toDomain()

        // when
        cart.addProduct(product1)
        cart.addProduct(product2)
        cart.addProduct(product2)

        // then
        assertThat(cart.items.map { it.product }).contains(product1, product2, product2)
    }

    @Test
    fun `removes products from cart`() {
        // given
        val cart = Cart.empty()
        val product1 = ExampleProduct(id = 1).toDomain()
        val product2 = ExampleProduct(id = 2).toDomain()

        // when
        cart.addProduct(product1)
        cart.addProduct(product2)
        cart.addProduct(product2)
        cart.removeProduct(product2)

        // then
        assertThat(cart.items.map { it.product }).contains(product1, product2)
    }

    @Test
    fun `adding products to cart updates total`() {
        // given
        val cart = Cart.empty()
        val product1 = ExampleProduct(priceInCents = 300).toDomain()
        val product2 = ExampleProduct(priceInCents = 1300).toDomain()

        // when
        cart.addProduct(product1)
        cart.addProduct(product2)

        // then
        assertThat(cart.totalInCents).isEqualTo(1600)
    }

    @Test
    fun `removing products from cart updates total`() {
        // given
        val cart = Cart.empty()
        val product1 = ExampleProduct(priceInCents = 300, id = 1).toDomain()
        val product2 = ExampleProduct(priceInCents = 1300, id = 2).toDomain()

        // when
        cart.addProduct(product1)
        cart.addProduct(product2)
        cart.removeProduct(product1)

        // then
        assertThat(cart.totalInCents).isEqualTo(1300)
    }
}