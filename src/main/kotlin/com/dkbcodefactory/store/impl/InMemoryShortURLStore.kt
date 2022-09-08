package com.dkbcodefactory.store.impl

import com.dkbcodefactory.store.ShortURLStore
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class InMemoryShortURLStore : ShortURLStore {
    override fun get(key: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun add(url: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun getStoreValues(): Flux<Pair<String, String>> {
        TODO("Not yet implemented")
    }
}