package com.dkbcodefactory.service.impl

import com.dkbcodefactory.repo.URLShortnerRepository
import com.dkbcodefactory.service.UrlShortnerService
import reactor.core.publisher.Mono

class UrlShortnerServiceImpl(private val urlShortnerRepository: URLShortnerRepository) : UrlShortnerService {
    override fun createShortUrl(url: String): Mono<String> {
        return urlShortnerRepository.addUrl(url)
    }

    override fun getUrl(key: String): Mono<String> {
        return urlShortnerRepository.findByKey(key)
    }
}