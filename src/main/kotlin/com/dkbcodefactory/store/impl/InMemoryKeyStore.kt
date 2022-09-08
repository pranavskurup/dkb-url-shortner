package com.dkbcodefactory.store.impl

import com.dkbcodefactory.store.KeyStore
import reactor.core.publisher.Flux

class InMemoryKeyStore : KeyStore {
    override fun getKey(): String {
        TODO("Not yet implemented")
    }

    override fun getStoreValues(): Flux<String> {
        TODO("Not yet implemented")
    }

}