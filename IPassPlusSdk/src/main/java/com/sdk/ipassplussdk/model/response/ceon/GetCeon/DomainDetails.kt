package com.sdk.ipassplussdk.model.response.ceon.GetCeon

data class DomainDetails(
    val accept_all: Boolean,
    val created: String,
    val custom: Boolean,
    val disposable: Boolean,
    val dmarc_enforced: Boolean,
    val domain: String,
    val expires: String,
    val free: Boolean,
    val registered: Boolean,
    val registered_to: String,
    val registrar_name: String,
    val spf_strict: Boolean,
    val suspicious_tld: Boolean,
    val tld: String,
    val updated: String,
    val valid_mx: Boolean,
    val website_exists: Boolean
)