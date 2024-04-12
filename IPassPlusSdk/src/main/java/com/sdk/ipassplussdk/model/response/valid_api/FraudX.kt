package com.sdk.ipassplussdk.model.response.valid_api

data class FraudX(
    val alert: Boolean,
    val global_traces: GlobalTraces,
    val local_traces: LocalTracesX,
    val warnings: List<Any>
)