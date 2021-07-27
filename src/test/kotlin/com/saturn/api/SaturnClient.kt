package com.saturn.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.net.URI

@Component
class SaturnClient {
    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var applicationContext: ApplicationContext

    fun fetchRecipes() =
        webClient
            .get()
            .uri(endpointUrl("/recipes"))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

    fun fetchCart(cartId: Long) =
        webClient
            .get()
            .uri(endpointUrl("/carts/$cartId"))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

    fun addRecipeToCart(cartId: Long, recipeId: Long) =
        webClient
            .post()
            .uri(endpointUrl("/carts/$cartId/add_recipe"))
            .bodyValue(CartsController.AddRecipeRequest(recipeId))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

    fun deleteRecipeFromCart(cartId: Long, recipeId: Long) =
        webClient
            .delete()
            .uri(endpointUrl("/carts/$cartId/delete_recipe/$recipeId"))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

    private fun endpointUrl(relativePath: String): URI {
        val port = applicationContext.environment.getProperty("local.server.port")
        return URI("http://localhost:$port$relativePath")
    }
}