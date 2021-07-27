package com.saturn.domain

import org.springframework.data.repository.Repository

interface ProductRepository : Repository<Product, Long> {
    fun save(product: Product)
}
