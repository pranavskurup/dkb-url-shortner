package com.dkbcodefactory.store.impl

import com.dkbcodefactory.exception.KeyGenerationException
import com.dkbcodefactory.store.KeyStore
import com.dkbcodefactory.util.KeyGenerator
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class InMemoryKeyStore(private val keyGenerator: KeyGenerator, private val inMemoryCacheSize: Int) : KeyStore {
    companion object {
        private val IN_MEMORY_STORE = LinkedBlockingDeque<String>()

        fun clear() {
            IN_MEMORY_STORE.clear()
        }
    }

    fun populateKeys() {
        var count = IN_MEMORY_STORE.size
        var iterations = 0
        while (count < inMemoryCacheSize / 2) {
            iterations++
            keyGenerator.generateKeys(inMemoryCacheSize - count).forEach { key ->
                if (!IN_MEMORY_STORE.contains(key)) {
                    IN_MEMORY_STORE.add(key)
                }
            }
            count = IN_MEMORY_STORE.size
            if (iterations > 10 * inMemoryCacheSize) {
                throw KeyGenerationException("Error populating cache")
            }
        }
    }

    override fun getKey(): String {
        populateKeys()
        return IN_MEMORY_STORE.poll(1, TimeUnit.MINUTES)!!
    }

    override fun getStoreValues(): Flux<String> {
        populateKeys()
        return Flux.fromStream(IN_MEMORY_STORE.stream())
    }

}