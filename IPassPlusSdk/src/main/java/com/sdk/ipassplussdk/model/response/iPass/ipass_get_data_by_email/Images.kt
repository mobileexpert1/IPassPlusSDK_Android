package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class Images(
    val availableSourceList: List<AvailableSource>,
    val fieldList: List<Field>
)