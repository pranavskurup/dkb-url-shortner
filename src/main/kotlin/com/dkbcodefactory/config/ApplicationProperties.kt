package com.dkbcodefactory.config

import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "dkbcodefactory")
@Data
class ApplicationProperties(
    var inMemoryCacheSize: Int = 10,
    var shortKeyLength: Int = 10
)