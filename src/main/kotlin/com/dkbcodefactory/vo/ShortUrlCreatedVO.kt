package com.dkbcodefactory.vo

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data

@Data
class ShortUrlCreatedVO(var url: String, @JsonProperty("short-url") var shortUrl: String)