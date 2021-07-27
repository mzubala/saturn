package com.saturn.api

import com.saturn.IntegrationTest
import com.saturn.domain.ProductRepository
import com.saturn.domain.RecipeRepository
import com.saturn.examples.ExampleProduct
import com.saturn.examples.ExampleRecipe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec
import org.springframework.test.web.reactive.server.expectBodyList


@IntegrationTest
class RecipesControllerTest {

    @Autowired
    lateinit var client: SaturnClient

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun `returns all recipes`() {
        // given
        val product1 = ExampleProduct().toDomain()
        val product2 = ExampleProduct().toDomain()
        productRepository.save(product1)
        productRepository.save(product2)
        val recipe1 = ExampleRecipe(products = listOf(product1, product2)).toDomain()
        val recipe2 = ExampleRecipe(products = listOf(product2)).toDomain()
        recipeRepository.save(recipe1)
        recipeRepository.save(recipe2)

        // when
        val recipesResponse = client.fetchRecipes()

        // then
        recipesResponse
            .expectStatus().is2xxSuccessful
            .expectBodyList<RecipesController.Recipe>()
            .isEqualTo<ListBodySpec<RecipesController.Recipe>>(listOf(recipe1, recipe2).map {
                RecipesController.Recipe(
                    it.id!!, it.name!!,
                    it.products.map { RecipesController.Product(it.id!!, it.name!!, it.priceInCents!!) }
                )
            })
    }
}