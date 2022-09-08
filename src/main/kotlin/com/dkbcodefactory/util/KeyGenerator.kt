package com.dkbcodefactory.util

interface KeyGenerator {
    fun generateKey(): String?
    fun generateKeys(quantity: Int): List<String?>?
}