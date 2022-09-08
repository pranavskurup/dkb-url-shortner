package com.dkbcodefactory.store.impl

import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import com.dkbcodefactory.store.KeyStore
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

internal class InMemoryShortURLStoreTest {

    @Test
    fun getWithMissingKey() {
        InMemoryShortURLStore.clear()
        val keyStore = Mockito.mock(KeyStore::class.java)
        var error = Assertions.assertThrows(KeyNotFoundException::class.java) {
            var shortURLStore = InMemoryShortURLStore(keyStore)
            shortURLStore.get("test")
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Short url with key: 'test' not found."
        )
    }

    @Test
    fun getWithExistingKey() {
        InMemoryShortURLStore.clear()
        val keyStore = Mockito.mock(KeyStore::class.java)
        var shortURLStore = InMemoryShortURLStore(keyStore)
        `when`(keyStore.getKey()).thenReturn("test")
        shortURLStore.add("test").block()
        val result = shortURLStore.get("test")
        Assert.assertNotNull("Expected result is not null", result)
        Assert.assertNotNull("Expected result is not null", result.block())
        Assert.assertTrue("Invalid result on passed key", result.block() == "test")
    }

    @Test
    fun addWithExistingKey() {
        InMemoryShortURLStore.clear()
        val keyStore = Mockito.mock(KeyStore::class.java)
        var shortURLStore = InMemoryShortURLStore(keyStore)
        val i = AtomicInteger(0)
        `when`(keyStore.getKey()).thenReturn("test-${i.get()}")
        shortURLStore.add("test").block()
        `when`(keyStore.getKey()).then { "test-${i.getAndIncrement()}" }
        shortURLStore.add("test2").block()
        val result = shortURLStore.get("test-0")
        Assert.assertNotNull("Expected result is not null", result)
        Assert.assertNotNull("Expected result is not null", result.block())
        Assert.assertTrue("Invalid result on passed key", result.block() == "test")
    }

    @Test
    fun addExistingValue() {
        InMemoryShortURLStore.clear()
        val keyStore = Mockito.mock(KeyStore::class.java)

        var error = Assertions.assertThrows(URLAlreadyExists::class.java) {
            var shortURLStore = InMemoryShortURLStore(keyStore)
            `when`(keyStore.getKey()).thenReturn("test")
            shortURLStore.add("test").block()
            shortURLStore.add("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Already url: 'test' with key: 'test' exists."
        )
    }

    @Test
    fun addNonExistingValue() {
        InMemoryShortURLStore.clear()
        val keyStore = Mockito.mock(KeyStore::class.java)
        `when`(keyStore.getKey()).thenReturn("test")
        var shortURLStore = InMemoryShortURLStore(keyStore)
        val result = shortURLStore.add("test")
        Assert.assertNotNull("Expected result is not null", result)
        Assert.assertNotNull("Expected result is not null", result.block())
        Assert.assertTrue("Invalid result on passed key", result.block() == "test")

    }

    @Test
    fun getStoreValuesTest() {
        InMemoryShortURLStore.clear()
        val keyStore = Mockito.mock(KeyStore::class.java)
        var shortURLStore = InMemoryShortURLStore(keyStore)
        val i = AtomicInteger(0)
        `when`(keyStore.getKey()).then { "test-${i.getAndIncrement()}" }
        shortURLStore.add("test1").block()
        shortURLStore.add("test2").block()
        val result = shortURLStore.getStoreValues().collect(Collectors.toList()).block()
        Assert.assertNotNull("Expected result is not null", result)
        Assert.assertTrue("Expected result is not empty", result!!.size == 2)
    }
}