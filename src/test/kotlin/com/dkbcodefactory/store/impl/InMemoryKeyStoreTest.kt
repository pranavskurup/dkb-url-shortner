package com.dkbcodefactory.store.impl

import org.junit.jupiter.api.Test
import org.springframework.util.Assert

internal class InMemoryKeyStoreTest {

    @Test
    fun getKeyTest() {
        val keyStore = InMemoryKeyStore()
        val key = keyStore.getKey()
        Assert.notNull(key, "Short key string from store is null")
        Assert.isTrue(key.trim().isNotEmpty(), "Short key string from store is empty")
    }


    @Test
    fun getStoreValuesTest() {
        val keyStore = InMemoryKeyStore()
        val values = keyStore.getStoreValues()
        Assert.notNull(values, "values in store is null")
        Assert.notNull(values.collectList().block(), "values in store is null")
        Assert.isTrue(!values.collectList().block()?.isEmpty()!!, "values in store is empty")
    }
}