package com.dkbcodefactory.store.impl

import com.dkbcodefactory.exception.KeyGenerationException
import com.dkbcodefactory.util.KeyGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.util.Assert
import java.util.stream.Collectors

internal class InMemoryKeyStoreTest {

    @Test
    fun getKeyTest() {
        InMemoryKeyStore.clear()
        val keyGenerator = Mockito.mock(KeyGenerator::class.java)
        var inputData =
            setOf("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10")
        Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
        val keyStore = InMemoryKeyStore(keyGenerator, 10)
        val key = keyStore.getKey()
        Assert.notNull(key, "Short key string from store is null")
        Assert.isTrue(key == inputData.first(), "Short key string from store is empty")
    }


    @Test
    fun getStoreValuesTest() {
        InMemoryKeyStore.clear()
        val keyGenerator = Mockito.mock(KeyGenerator::class.java)
        var inputData =
            setOf("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10")
        Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
        val keyStore = InMemoryKeyStore(keyGenerator, 10)
        val values = keyStore.getStoreValues()
        Assert.notNull(values, "values in store is null")
        val list = values.toStream().collect(Collectors.toSet())
        Assert.notNull(list, "values in store is null")
        Assert.isTrue(!list?.isEmpty()!!, "values in store is empty")
        Assert.isTrue(inputData == list, "values in store is empty")
    }

    @Test
    fun keyGenerationException() {
        InMemoryKeyStore.clear()
        var error = Assertions.assertThrows(KeyGenerationException::class.java) {
            val keyGenerator = Mockito.mock(KeyGenerator::class.java)
            var inputData = HashSet<String>()
            Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
            val keyStore = InMemoryKeyStore(keyGenerator, 10)
            keyStore.getKey()
        }
        Assert.isTrue(error.message == "Error populating cache", "Error message is incorrect")

    }

    @Test
    fun clearkeyGeneration() {
        InMemoryKeyStore.clear()
        val keyGenerator = Mockito.mock(KeyGenerator::class.java)
        var inputData = setOf("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10")
        Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
        val keyStore = InMemoryKeyStore(keyGenerator, 10)
        val values = keyStore.getStoreValues()
        Assert.notNull(values, "values in store is null")
        val list = values.toStream().collect(Collectors.toSet())
        Assert.notNull(list, "values in store is null")
        Assert.isTrue(!list?.isEmpty()!!, "values in store is empty")
        Assert.isTrue(inputData == list, "values in store is empty")


        InMemoryKeyStore.clear()
        inputData = HashSet<String>()
        Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
        var error = Assertions.assertThrows(KeyGenerationException::class.java) {
            val keyGenerator = Mockito.mock(KeyGenerator::class.java)
            var inputData = HashSet<String>()
            Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
            val keyStore = InMemoryKeyStore(keyGenerator, 10)
            keyStore.getKey()
        }
        Assert.isTrue(error.message == "Error populating cache", "Error message is incorrect")
    }
}