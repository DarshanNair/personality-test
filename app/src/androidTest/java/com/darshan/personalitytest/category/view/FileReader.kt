package com.darshan.personalitytest.category.view

import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readStringFromFile(testInstance: Any, fileName: String): String {
        try {
            val classLoader = testInstance.javaClass.classLoader
            val inputStream = classLoader.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream)
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}