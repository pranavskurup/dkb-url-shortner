package com.dkbcodefactory.exception

class InvalidUrlException(url: String) : DKBUrlShortnerException("Invalid url specified for shortening '$url'")
