package com.darshan.coretesting

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object MockServerDispatcher {

    fun SUCCESS_REQUEST(clazz: Class<*>, fileName: String) = request(clazz, fileName, 200)

    fun ERROR_REQUEST(clazz: Class<*>, fileName: String) = request(clazz, fileName, 401)

    private fun request(clazz: Class<*>, fileName: String, errorCode: Int) = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val json = FileReader.readStringFromFile(clazz, fileName)
            return MockResponse()
                .setResponseCode(errorCode)
                .setBody(json)
        }
    }

}