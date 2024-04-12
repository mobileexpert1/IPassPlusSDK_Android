package com.sdk.ipassplussdk.apis

interface ResultListener<T> {
    fun onSuccess(response: T?)
    fun onError(exception: String)
}
