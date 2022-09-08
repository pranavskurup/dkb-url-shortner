package com.dkbcodefactory.service

import reactor.core.publisher.Mono

interface UrlShortnerService {
    fun createShortUrl(url: String): Mono<String>
    fun getUrl(key: String): Mono<String>
}