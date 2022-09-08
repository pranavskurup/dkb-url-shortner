package com.dkbcodefactory.repo.impl

import com.dkbcodefactory.repo.URLShortnerRepository
import com.dkbcodefactory.store.ShortURLStore
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class URLShortnerRepositoryImpl(private val shortURLStore: ShortURLStore) : URLShortnerRepository {

    override fun findByKey(key: String): Mono<String> {
        return shortURLStore.get(key)
    }

    override fun addUrl(url: String): Mono<String> {
        return shortURLStore.add(url)
    }

}