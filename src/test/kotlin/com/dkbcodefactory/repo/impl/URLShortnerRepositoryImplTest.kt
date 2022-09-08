package com.dkbcodefactory.repo.impl

import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import com.dkbcodefactory.store.ShortURLStore
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import reactor.core.publisher.Mono

internal class URLShortnerRepositoryImplTest {

    @Test
    fun findByKeyWithExistingKey() {
        val shortURLStore = Mockito.mock(ShortURLStore::class.java)
        `when`(shortURLStore.get("test")).thenReturn(Mono.just("test"))
        val urlShortnerRepository = URLShortnerRepositoryImpl(shortURLStore)
        val result = urlShortnerRepository.findByKey("test").block()
        Assert.assertNotNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")
    }

    @Test
    fun findByKeyWithNotExistingKey() {
        val error = Assert.assertThrows(KeyNotFoundException::class.java) {
            val shortURLStore = Mockito.mock(ShortURLStore::class.java)
            `when`(shortURLStore.get("test")).thenReturn(Mono.error(KeyNotFoundException("test")))
            val urlShortnerRepository = URLShortnerRepositoryImpl(shortURLStore)
            val result = urlShortnerRepository.findByKey("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Short url with key: 'test' not found."
        )
    }

    @Test
    fun addUrlWithExistingURL() {
        val shortURLStore = Mockito.mock(ShortURLStore::class.java)
        `when`(shortURLStore.add("test")).thenReturn(Mono.just("test"))
        val urlShortnerRepository = URLShortnerRepositoryImpl(shortURLStore)
        val result = urlShortnerRepository.addUrl("test").block()
        Assert.assertNotNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")

    }

    @Test
    fun addUrlWithNonExistingURL() {
        val error = Assert.assertThrows(URLAlreadyExists::class.java) {
            val shortURLStore = Mockito.mock(ShortURLStore::class.java)
            `when`(shortURLStore.add("test")).thenReturn(Mono.error(URLAlreadyExists("test", "test")))
            val urlShortnerRepository = URLShortnerRepositoryImpl(shortURLStore)
            val result = urlShortnerRepository.addUrl("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Already url: 'test' with key: 'test' exists."
        )
    }
}