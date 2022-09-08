package com.dkbcodefactory.store

import reactor.core.publisher.Flux

interface KeyStore {
    fun getKey(): String
    fun getStoreValues(): Flux<String>
}