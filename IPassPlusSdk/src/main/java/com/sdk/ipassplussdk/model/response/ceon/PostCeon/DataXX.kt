package com.sdk.ipassplussdk.model.response.ceon.PostCeon

data class DataXX(
    val applied_rules: List<Any>,
    val city: String,
    val country: String,
    val harmful: Boolean,
    val ip: String,
    val isp_name: String,
    val latitude: Int,
    val longitude: Int,
    val open_ports: List<Any>,
    val public_proxy: Boolean,
    val score: Int,
    val spam_number: Int,
    val spam_urls: List<Any>,
    val state_prov: String,
    val timezone_offset: String,
    val tor: Boolean,
    val type: String,
    val vpn: Boolean,
    val web_proxy: Boolean
)