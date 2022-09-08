package com.dkbcodefactory.util

import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

class CustomKeyGenerator(private val shortKeyLength: Int) : KeyGenerator {
    companion object {
        val CHARACTERS = charArrayOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9'
        )
        private val RANDOM = Random()
        val UPPER_BOUND_FOR_RANDOM = CHARACTERS.size
        fun getRandomCharacter(): Char {
            return CHARACTERS[RANDOM.nextInt(UPPER_BOUND_FOR_RANDOM)]
        }
    }

    override fun generateKey(): String {
        val key = StringBuilder()
        for (i in 0..shortKeyLength) {
            key.append(getRandomCharacter())
        }
        return key.toString()
    }

    override fun generateKeys(quantity: Int): Set<String> {
        return IntStream.range(0, shortKeyLength).mapToObj { generateKey() }.collect(Collectors.toSet())
    }

}