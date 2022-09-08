package com.dkbcodefactory.util

import org.junit.jupiter.api.Test
import org.springframework.util.Assert

internal class CustomKeyGeneratorTest {

    @Test
    fun generateKey() {
        val customKeyGenerator = CustomKeyGenerator()
        val result = customKeyGenerator.generateKey()
        Assert.notNull(result, "Expecting non null string")
        result?.trim()?.let { Assert.isTrue(it.isNotEmpty(), "Expecting non empty string") }
    }

    @Test
    fun generateKeys() {
        val customKeyGenerator = CustomKeyGenerator()
        val result = customKeyGenerator.generateKeys(10)
        Assert.notNull(result, "Expecting non null list")
        Assert.isTrue(result!!.size == 10, "Expecting list with specified count")
        result.let {
            {
                Assert.isTrue(it.isNotEmpty(), "Expecting non empty string")
            }
        }
    }
}