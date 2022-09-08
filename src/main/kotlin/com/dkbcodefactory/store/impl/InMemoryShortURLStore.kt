package com.dkbcodefactory.store.impl

import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import com.dkbcodefactory.store.KeyStore
import com.dkbcodefactory.store.ShortURLStore
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

class InMemoryShortURLStore(private val keyStore: KeyStore) : ShortURLStore {
    companion object {
        private val KEY_URL_STORE = ConcurrentHashMap<String, String>()
        fun clear() {
            KEY_URL_STORE.clear()
        }
    }

    override fun get(key: String): Mono<String> {
        if (!KEY_URL_STORE.containsKey(key)) {
            throw KeyNotFoundException(key)
        }
        return Mono.just(KEY_URL_STORE[key]!!)
    }

    override fun add(url: String): Mono<String> {
        if (KEY_URL_STORE.containsValue(url)) {
            val first = KEY_URL_STORE.filter { entry -> entry.value == url }.map { entry -> entry.key }.first()
            return Mono.error(URLAlreadyExists(first, url))
        }
        var key: String?
        while (true) {
            key = keyStore.getKey()
            if (KEY_URL_STORE.containsKey(key))
                continue
            KEY_URL_STORE[key] = url
            break
        }
        return Mono.just(key!!)
    }

    override fun getStoreValues(): Flux<Pair<String, String>> {
        return Flux.fromStream(KEY_URL_STORE.entries.stream().map { entry -> Pair(entry.key, entry.value) })
    }
}