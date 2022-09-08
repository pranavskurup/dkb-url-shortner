package com.dkbcodefactory.vo

import com.fasterxml.jackson.annotation.JsonProperty

class ShortUrlCreatedVO(val url: String, @JsonProperty("short-url") shortUrl: String)