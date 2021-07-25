package com.darshan.personalitytest.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject internal constructor() : ViewModel() {

    val state = MutableLiveData<String>()

}