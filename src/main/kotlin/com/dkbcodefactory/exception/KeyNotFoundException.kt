package com.dkbcodefactory.exception

class KeyNotFoundException(key: String) : DKBUrlShortnerException("Short url with key: '$key' not found.")