package com.sdk.ipassplussdk.model.response.valid_api

data class DigitalForgeries(
    val alert: Boolean,
    val score: Double,
    val threshold: Double
)