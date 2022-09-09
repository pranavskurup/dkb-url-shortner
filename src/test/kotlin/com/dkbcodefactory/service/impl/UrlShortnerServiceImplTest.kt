package com.dkbcodefactory.service.impl

import com.dkbcodefactory.exception.InvalidUrlException
import com.dkbcodefactory.exception.KeyNotFoundException
import com.dkbcodefactory.exception.URLAlreadyExists
import com.dkbcodefactory.repo.URLShortnerRepository
import org.apache.commons.validator.routines.UrlValidator
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import reactor.core.publisher.Mono

internal class UrlShortnerServiceImplTest {

    @Test
    fun getUrlExistingKey() {
        val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
        Mockito.`when`(urlShortnerRepository.findByKey("test")).thenReturn(Mono.just("test"))
        val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository, urlValidator())
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
            val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository, urlValidator())
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
        Mockito.`when`(urlShortnerRepository.addUrl("https://github.com/")).thenReturn(Mono.just("test"))
        val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository, urlValidator())
        val result = urlShortnerService.createShortUrl("https://github.com/").block()
        Assert.assertNotNull("Expected not null result ", result)
        Assert.assertTrue("Expected not empty result ", result!!.isNotEmpty())
        Assert.assertTrue("Expected not empty result ", result == "test")

    }

    @Test
    fun createShortUrlNonExistingURL() {
        val error = Assert.assertThrows(URLAlreadyExists::class.java) {
            val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
            Mockito.`when`(urlShortnerRepository.addUrl("https://github.com/"))
                .thenReturn(Mono.error(URLAlreadyExists("test", "https://github.com/")))
            val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository, urlValidator())
            val result = urlShortnerService.createShortUrl("https://github.com/").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Already url: 'https://github.com/' with key: 'test' exists."
        )
    }

    @Test
    fun createShortUrlInvalidURL() {
        val error = Assert.assertThrows(InvalidUrlException::class.java) {
            val urlShortnerRepository = Mockito.mock(URLShortnerRepository::class.java)
            Mockito.`when`(urlShortnerRepository.addUrl("test"))
                .thenReturn(Mono.error(URLAlreadyExists("test", "test")))
            val urlShortnerService = UrlShortnerServiceImpl(urlShortnerRepository, urlValidator())
            val result = urlShortnerService.createShortUrl("test").block()
        }
        Assert.assertTrue(
            "Message for key not found is incorrect",
            error.message == "Invalid url specified for shortening 'test'"
        )
    }

    fun urlValidator(): UrlValidator {
        val schemes = arrayOf("http", "https")
        return UrlValidator(schemes)
    }
}