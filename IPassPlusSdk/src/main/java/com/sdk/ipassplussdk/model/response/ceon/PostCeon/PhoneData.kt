package com.sdk.ipassplussdk.model.response.ceon.PostCeon

data class PhoneData(
    val __v: Int,
    val _id: String,
    val account_details: AccountDetailsX,
    val applied_rules: List<AppliedRule>,
    val carrier: Any,
    val country: String,
    val disposable: Boolean,
    val number: Int,
    val score: Int,
    val sesid: String,
    val type: String,
    val valid: Boolean
)