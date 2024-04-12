package com.sdk.ipassplussdk.documentreader

import com.regula.documentreader.api.errors.DocumentReaderException
import com.regula.documentreader.api.results.DocumentReaderResults

interface DocumentReaderCompletion {
    fun onCompleted(action: Int, results: DocumentReaderResults?, error: DocumentReaderException?)
}