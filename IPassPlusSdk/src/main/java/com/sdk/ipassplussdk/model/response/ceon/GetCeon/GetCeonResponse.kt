package com.sdk.ipassplussdk.model.response.ceon.GetCeon

data class GetCeonResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)