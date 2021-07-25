package com.darshan.personalitytest.main

import android.app.ProgressDialog
import android.content.Context
import com.darshan.personalitytest.R

class SubmitProgress(context: Context) : ProgressDialog(context) {

    init {
        setMessage(context.getString(R.string.submitting_text))
        setCancelable(false)
    }

}