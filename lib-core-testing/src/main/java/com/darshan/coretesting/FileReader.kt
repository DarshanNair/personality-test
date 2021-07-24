package com.darshan.coretesting

import java.io.FileNotFoundException
import java.io.InputStreamReader

object FileReader {
    fun readStringFromFile(clazz: Class<*>, fileName: String): String {
        val inputStream =
            clazz.classLoader?.getResourceAsStream(fileName) ?: throw FileNotFoundException()
        val builder = StringBuilder()
        InputStreamReader(inputStream).readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}