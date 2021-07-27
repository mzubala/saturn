package com.saturn.domain

import com.saturn.domain.Cart.Companion.WITH_ITEMS_AND_PRODUCT
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.NamedSubgraph
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "carts")
@NamedEntityGraph(
    name = WITH_ITEMS_AND_PRODUCT, attributeNodes = [
        NamedAttributeNode(value = "items", subgraph = "product-subgraph")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "product-subgraph",
            attributeNodes = [
                NamedAttributeNode(value = "product")
            ]
        )
    ]
)
class Cart {
    companion object {
        fun empty(): Cart {
            val cart = Cart()
            cart.totalInCents = 0
            return cart
        }

        const val WITH_ITEMS_AND_PRODUCT = "WITH_ITEMS_AND_PRODUCT"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "total_in_cents")
    @NotNull
    @Min(0)
    var totalInCents: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    @NotNull
    var items: MutableList<CartItem> = emptyList<CartItem>().toMutableList()

    fun addProduct(product: Product) {
        this.items.add(CartItem.of(product))
        this.totalInCents = this.totalInCents!! + product.priceInCents!!
    }

    fun removeProduct(product: Product) {
        val iterator = this.items.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if(item.product!!.id == product.id) {
                iterator.remove()
                this.totalInCents = this.totalInCents!! - product.priceInCents!!
                break
            }
        }
    }
}

@Entity
@Table(name = "cart_items")
class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @NotNull
    var product: Product? = null

    companion object {
        fun of(product: Product): CartItem {
            val item = CartItem()
            item.product = product
            return item
        }
    }
}