package com.dkbcodefactory.repo.impl

import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class URLShortnerRepositoryImplTest {

    @Test
    fun findByKeyWithExistingKey() {
        val urlShortnerRepository = URLShortnerRepositoryImpl()
        val result = urlShortnerRepository.findByKey("test").block()
        Assert.assertNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")
    }

    @Test
    fun findByKeyWithNotExistingKey() {
        val error = Assert.assertThrows(KeyNotFoundException::class.java) {
            val urlShortnerRepository = URLShortnerRepositoryImpl()
            val result = urlShortnerRepository.findByKey("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Short url with key: 'test' not found."
        )
    }

    @Test
    fun addUrlWithExistingURL() {
        val urlShortnerRepository = URLShortnerRepositoryImpl()
        val result = urlShortnerRepository.addUrl("test").block()
        Assert.assertNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")

    }

    @Test
    fun addUrlWithNonExistingURL() {
        val error = Assert.assertThrows(URLAlreadyExists::class.java) {
            val urlShortnerRepository = URLShortnerRepositoryImpl()
            val result = urlShortnerRepository.addUrl("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Already url: 'test' with key: 'test' exists."
        )
    }
}