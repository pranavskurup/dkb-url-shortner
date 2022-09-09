package com.dkbcodefactory.service.impl

import com.dkbcodefactory.exception.InvalidUrlException
import com.dkbcodefactory.repo.URLShortnerRepository
import com.dkbcodefactory.service.UrlShortnerService
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UrlShortnerServiceImpl(
    private val urlShortnerRepository: URLShortnerRepository,
    private val urlValidator: UrlValidator
) :
    UrlShortnerService {
    override fun createShortUrl(url: String): Mono<String> {
        if (!urlValidator.isValid(url)) {
            return Mono.error(InvalidUrlException(url))
        }
        return urlShortnerRepository.addUrl(url)
    }

    override fun getUrl(key: String): Mono<String> {
        return urlShortnerRepository.findByKey(key)
    }
}