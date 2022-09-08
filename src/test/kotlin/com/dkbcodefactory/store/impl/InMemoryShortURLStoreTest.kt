package com.dkbcodefactory.store.impl

import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

internal class InMemoryShortURLStoreTest {

    @Test
    fun getWithMissingKey() {
        var error = Assertions.assertThrows(KeyNotFoundException::class.java) {
            var shortURLStore = InMemoryShortURLStore()
            shortURLStore.get("test")
        }
        Assert.isTrue(
            error.message == "Short url with key: 'test' not found.",
            "Message for key not found is incorrect"
        )
    }

    @Test
    fun getWithExistingKey() {
        var shortURLStore = InMemoryShortURLStore()
        val result = shortURLStore.get("test")
        Assert.notNull(result, "Expected result is not null")
        Assert.notNull(result.block(), "Expected result is not null")
        Assert.isTrue(result.block() == "test", "Invalid result on passed key")
    }

    @Test
    fun addExistingValue() {
        var error = Assertions.assertThrows(URLAlreadyExists::class.java) {
            var shortURLStore = InMemoryShortURLStore()
            shortURLStore.add("test")
        }
        Assert.isTrue(
            error.message == "Already url: 'test' with key: 'test' exists.",
            "Message for key not found is incorrect"
        )
    }

    @Test
    fun addNonExistingValue() {
        var shortURLStore = InMemoryShortURLStore()
        val result = shortURLStore.add("test")
        Assert.notNull(result, "Expected result is not null")
        Assert.notNull(result.block(), "Expected result is not null")
        Assert.isTrue(result.block() == "test", "Invalid result on passed key")

    }
}