package com.saturn

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class App {

}

fun main() {
    SpringApplication.run(App::class.java)
}