package com.sdk.ipassplussdk.model.response.valid_api

data class Fraud(
    val alert: Boolean,
    val global_traces: GlobalTraces,
    val local_traces: LocalTraces,
    val warnings: List<Any>
)