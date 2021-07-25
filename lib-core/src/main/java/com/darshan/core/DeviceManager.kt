package com.darshan.core

import android.content.Context

class DeviceManager(val context: Context) {

    val isTablet: Boolean
        get() = context.resources.getBoolean(R.bool.isTablet)

}