package com.dkbcodefactory.controller

import com.dkbcodefactory.dto.CreateShortUrlRequest
import com.dkbcodefactory.service.UrlShortnerService
import com.dkbcodefactory.vo.ShortUrlCreatedVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI


@RestController
class UrlShortnerController(@Autowired val urlShortnerService: UrlShortnerService) {
    @PostMapping("/api/v1/url-shortner")
    @ResponseStatus(HttpStatus.CREATED)
    fun createShortUrl(@RequestBody createShortUrlRequest: CreateShortUrlRequest): Mono<ShortUrlCreatedVO> {

        return urlShortnerService
            .createShortUrl(createShortUrlRequest.url)
            .map { ShortUrlCreatedVO(createShortUrlRequest.url, it) }
    }

    @GetMapping("/api/v1/url-shortner/{key}")
    fun redirectFromShortUrl(@PathVariable("key") key: String, response: ServerHttpResponse): Mono<Void> {
        return urlShortnerService
            .getUrl(key).flatMap {
                response.statusCode = HttpStatus.PERMANENT_REDIRECT
                response.headers.location = URI(it!!)
                response.setComplete()
            }
    }
}