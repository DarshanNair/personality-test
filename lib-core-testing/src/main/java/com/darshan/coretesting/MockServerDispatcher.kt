package com.darshan.coretesting

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object MockServerDispatcher {

    fun SUCCESS_REQUEST(clazz: Class<*>, fileName: String) = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val json = FileReader.readStringFromFile(clazz, fileName)
            return MockResponse()
                .setResponseCode(200)
                .setBody(json)
        }
    }

}