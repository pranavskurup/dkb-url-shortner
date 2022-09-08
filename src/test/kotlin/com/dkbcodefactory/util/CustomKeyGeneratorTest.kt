package com.dkbcodefactory.util

import org.junit.jupiter.api.Test
import org.springframework.util.Assert

internal class CustomKeyGeneratorTest {

    @Test
    fun generateKeyTest() {
        val customKeyGenerator = CustomKeyGenerator(10)
        val result = customKeyGenerator.generateKey()
        Assert.notNull(result, "Expecting non null string")
        result.trim().let { Assert.isTrue(it.isNotEmpty(), "Expecting non empty string") }
    }

    @Test
    fun generateKeysTest() {
        val customKeyGenerator = CustomKeyGenerator(10)
        val result = customKeyGenerator.generateKeys(10)
        Assert.notNull(result, "Expecting non null list")
        Assert.isTrue(result.size == 10, "Expecting list with specified count")
        result.forEach {
            Assert.isTrue(it.trim().isNotEmpty(), "Expecting non empty string")

        }
    }

    @Test
    fun getRandomCharacterTest() {
        Assert.isTrue(
            CustomKeyGenerator.CHARACTERS.size == CustomKeyGenerator.UPPER_BOUND_FOR_RANDOM, "Both Upper bound" +
                    " and length of characters should be same"
        )
        Assert.isTrue(
            HashSet<Char>(CustomKeyGenerator.CHARACTERS.asList()).size == CustomKeyGenerator
                .UPPER_BOUND_FOR_RANDOM, "Duplicate characters in variable 'CHARACTERS'"
        )

        Assert.isTrue(
            CustomKeyGenerator.CHARACTERS.contains(CustomKeyGenerator.getRandomCharacter()), "Generated " +
                    "character is in the variable 'CHARACTERS'"
        )
    }
}