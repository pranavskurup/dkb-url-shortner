package com.dkbcodefactory.util

import org.junit.Assert
import org.junit.jupiter.api.Test

internal class CustomKeyGeneratorTest {

    @Test
    fun generateKeyTest() {
        val customKeyGenerator = CustomKeyGenerator(10)
        val result = customKeyGenerator.generateKey()
        Assert.assertNotNull("Expecting non null string", result)
        result.trim().let { Assert.assertTrue("Expecting non empty string", it.isNotEmpty()) }
    }

    @Test
    fun generateKeysTest() {
        val customKeyGenerator = CustomKeyGenerator(10)
        val result = customKeyGenerator.generateKeys(10)
        Assert.assertNotNull("Expecting non null list", result)
        Assert.assertTrue("Expecting list with specified count", result.size == 10)
        result.forEach {
            Assert.assertTrue("Expecting non empty string", it.trim().isNotEmpty())

        }
    }

    @Test
    fun getRandomCharacterTest() {
        Assert.assertTrue(
            "Both Upper bound" +
                    " and length of characters should be same",
            CustomKeyGenerator.CHARACTERS.size == CustomKeyGenerator.UPPER_BOUND_FOR_RANDOM
        )
        Assert.assertTrue(
            "Duplicate characters in variable 'CHARACTERS'",
            HashSet<Char>(CustomKeyGenerator.CHARACTERS.asList()).size == CustomKeyGenerator
                .UPPER_BOUND_FOR_RANDOM
        )

        Assert.assertTrue(
            "Generated " +
                    "character is in the variable 'CHARACTERS'",
            CustomKeyGenerator.CHARACTERS.contains(CustomKeyGenerator.getRandomCharacter())
        )
    }
}