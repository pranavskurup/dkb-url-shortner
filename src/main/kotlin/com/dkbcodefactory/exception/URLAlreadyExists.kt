package com.dkbcodefactory.exception

class URLAlreadyExists(key: String, url: String) :
    DKBUrlShortnerException("Already url: '$url' with key: '$key' exists.")