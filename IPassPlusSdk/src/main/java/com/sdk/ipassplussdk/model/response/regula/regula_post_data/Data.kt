package com.sdk.ipassplussdk.model.response.regula.regula_post_data

data class Data(
    val ChipPage: Int,
    val ContainerList: ContainerList,
    val CoreLibResultCode: Int,
    val ProcessingFinished: Int,
    val TransactionInfo: TransactionInfo,
    val createdAt: String,
    val elapsedTime: Int,
    val email: String,
    val morePagesAvailable: Int,
    val passBackObject: Any,
    val sid: String
)