package com.saturn.domain

import com.saturn.domain.Recipe.Companion.WITH_PRODUCTS
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@NamedEntityGraph(
    name = WITH_PRODUCTS,
    attributeNodes = [
        NamedAttributeNode("products")
    ]
)
@Table(name = "recipes")
class Recipe {

    companion object {
        const val WITH_PRODUCTS = "WITH_PRODUCTS"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotEmpty
    var name: String? = null

    @ManyToMany
    @NotEmpty
    @JoinTable(
        name = "recipes_products",
        joinColumns = [JoinColumn(name = "recipe_id")],
        inverseJoinColumns = [JoinColumn(name="product_id")]
    )
    var products: MutableList<Product> = emptyList<Product>().toMutableList()
}