package com.dkbcodefactory.repo

import reactor.core.publisher.Mono

interface URLShortnerRepository {
    fun findByKey(key: String): Mono<String>
    fun addUrl(url: String): Mono<String>
}

