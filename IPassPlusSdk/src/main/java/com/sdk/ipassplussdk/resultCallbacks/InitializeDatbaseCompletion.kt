package com.sdk.ipassplussdk.resultCallbacks

import androidx.annotation.IntRange

interface InitializeDatabaseCompletion {
    fun onProgressChanged(@IntRange(from = 0L, to = 100L) progress: Int)

    fun onCompleted(status: Boolean, message: String?)

}