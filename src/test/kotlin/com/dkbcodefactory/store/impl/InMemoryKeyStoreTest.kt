package com.dkbcodefactory.store.impl

import com.dkbcodefactory.exception.KeyGenerationException
import com.dkbcodefactory.util.KeyGenerator
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
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
        Assert.assertNotNull("Short key string from store is null", key)
        Assert.assertTrue("Short key string from store is empty", key == inputData.first())
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
        Assert.assertNotNull("values in store is null", values)
        val list = values.toStream().collect(Collectors.toSet())
        Assert.assertNotNull("values in store is null", list)
        Assert.assertTrue("values in store is empty", !list?.isEmpty()!!)
        Assert.assertTrue("values in store is empty", inputData == list)
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
        Assert.assertTrue("Error message is incorrect", error.message == "Error populating cache")

    }

    @Test
    fun clearkeyGeneration() {
        InMemoryKeyStore.clear()
        val keyGenerator = Mockito.mock(KeyGenerator::class.java)
        var inputData = setOf("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10")
        Mockito.`when`(keyGenerator.generateKeys(10)).thenReturn(inputData)
        val keyStore = InMemoryKeyStore(keyGenerator, 10)
        val values = keyStore.getStoreValues()
        Assert.assertNotNull("values in store is null", values)
        val list = values.toStream().collect(Collectors.toSet())
        Assert.assertNotNull("values in store is null", list)
        Assert.assertTrue("values in store is empty", !list?.isEmpty()!!)
        Assert.assertTrue("values in store is empty", inputData == list)


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
        Assert.assertTrue("Error message is incorrect", error.message == "Error populating cache")
    }
}