package com.dkbcodefactory.service.impl

import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import com.dkbcodefactory.repo.URLShortnerRepository
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import reactor.core.publisher.Mono

internal class UrlShortnerServiceImplTest {

    @Test
    fun getUrlExistingKey() {
        val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
        Mockito.`when`(urlShortnerRepository.findByKey("test")).thenReturn(Mono.just("test"))
        val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository)
        val result = urlShortnerService.getUrl("test").block()
        Assert.assertNotNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")
    }

    @Test
    fun getUrlNotExistingKey() {
        val error = Assert.assertThrows(KeyNotFoundException::class.java) {
            val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
            Mockito.`when`(urlShortnerRepository.findByKey("test")).thenReturn(Mono.error(KeyNotFoundException("test")))
            val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository)
            val result = urlShortnerService.getUrl("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Short url with key: 'test' not found."
        )
    }

    @Test
    fun createShortUrlExistingURL() {
        val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
        Mockito.`when`(urlShortnerRepository.addUrl("test")).thenReturn(Mono.just("test"))
        val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository)
        val result = urlShortnerService.createShortUrl("test").block()
        Assert.assertNotNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")

    }

    @Test
    fun createShortUrlNonExistingURL() {
        val error = Assert.assertThrows(URLAlreadyExists::class.java) {
            val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
            Mockito.`when`(urlShortnerRepository.addUrl("test"))
                .thenReturn(Mono.error(URLAlreadyExists("test", "test")))
            val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository)
            val result = urlShortnerService.createShortUrl("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Already url: 'test' with key: 'test' exists."
        )
    }
}