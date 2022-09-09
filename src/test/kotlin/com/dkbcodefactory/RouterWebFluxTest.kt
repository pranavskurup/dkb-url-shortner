package com.dkbcodefactory

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters


@WebFluxTest
@ComponentScan("com.dkbcodefactory")
class RouterWebFluxTest(@Autowired val client: WebTestClient) {

    @Test
    fun createShortUrlTestWithoutBody() {
        client
            .post()
            .uri("/api/v1/url-shortner")
            .exchange()
            .expectStatus().is4xxClientError
    }

    @Test
    fun createShortUrlTestWithInvalidBody() {
        val map: LinkedHashMap<String, String> = LinkedHashMap<String, String>()
        client
            .post()
            .uri("/api/v1/url-shortner")
            .body(BodyInserters.fromValue(map))
            .exchange()
            .expectStatus().is4xxClientError
    }

    @Test
    fun createShortUrlTestWithValidBody() {
        val map: LinkedHashMap<String, String> = LinkedHashMap<String, String>()
        map.put("url", "https://www.linkedin.com/feed/")
        client
            .post()
            .uri("/api/v1/url-shortner")
            .body(BodyInserters.fromValue(map))
            .exchange()
            .expectStatus().isCreated
            .expectBody().jsonPath("short-url").isNotEmpty
    }

    @Test
    fun validateRedirection() {
        val map: LinkedHashMap<String, String> = LinkedHashMap<String, String>()
        map.put(
            "url",
            "https://stackoverflow.com/questions/47655789/how-to-make-reactive-webclient-follow-3xx-redirects"
        )
        val returnResult = client
            .post()
            .uri("/api/v1/url-shortner")
            .body(BodyInserters.fromValue(map))
            .exchange()
            .expectStatus().isCreated
            .returnResult(Map::class.java)
        val values = returnResult.responseBody.collectList().block()!![0]

        client
            .get()
            .uri("/api/v1/url-shortner/${values["short-url"]}")
            .exchange()
            .expectStatus().is3xxRedirection.expectHeader()
            .location("https://stackoverflow.com/questions/47655789/how-to-make-reactive-webclient-follow-3xx-redirects")
    }
}