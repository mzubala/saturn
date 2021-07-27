package com.saturn.domain

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.Repository

interface CartRepository : Repository<Cart, Long> {
    fun save(cart: Cart): Cart

    @EntityGraph(value = Cart.WITH_ITEMS_AND_PRODUCT)
    fun findById(id: Long): Cart
}