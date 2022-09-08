package com.dkbcodefactory.store

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ShortURLStore {
    fun get(key: String): Mono<String>
    fun add(url: String): Mono<String>
    fun getStoreValues(): Flux<Pair<String, String>>
}
