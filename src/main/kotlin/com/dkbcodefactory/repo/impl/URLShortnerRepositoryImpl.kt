package com.dkbcodefactory.repo.impl

import com.dkbcodefactory.repo.URLShortnerRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class URLShortnerRepositoryImpl : URLShortnerRepository {

    override fun findByKey(key: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun addUrl(url: String): Mono<String> {
        TODO("Not yet implemented")
    }

}