package com.dkbcodefactory.config

import org.apache.commons.validator.routines.UrlValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration(private val applicationProperties: ApplicationProperties) {
    @Bean
    fun inMemoryCacheSize(): Int {
        return applicationProperties.inMemoryCacheSize
    }

    @Bean
    fun shortKeyLength(): Int {
        return applicationProperties.shortKeyLength
    }

    @Bean
    fun urlValidator(): UrlValidator {
        val schemes = arrayOf("http", "https")
        return UrlValidator(schemes)
    }
}