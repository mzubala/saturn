package com.saturn.examples

import com.saturn.domain.Product

data class ExampleProduct(
    val id: Long? = null,
    val name: String = "test product",
    val priceInCents: Long = 200
) {
    fun toDomain(): Product {
        val product = Product()
        product.id = id
        product.name = name
        product.priceInCents = priceInCents
        return product
    }
}