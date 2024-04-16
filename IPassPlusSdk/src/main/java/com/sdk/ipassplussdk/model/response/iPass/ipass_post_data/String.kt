package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class String(
    val ALIGNMENT_SYMBOLS_IN_STRING: Int,
    val CHECK_SUMS: Int,
    val ErrorPOSITION: ErrorPOSITION,
    val FieldCount: Int,
    val Fields: List<FieldX>,
    val STRINGS_DISTANCE: Int,
    val STRINGS_INTERVAL: Int,
    val STRING_FILLING: Int,
    val STRING_POSITION: Int,
    val SYMBOLS_PARAM: Int,
    val SizeError_ALIGNMENT: Int,
    val SizeError_DISTANCE: Int,
    val SizeError_INTERVAL: Int,
    val StringAngle: Int,
    val StringBorders: StringBorders,
    val SymbolsCount: Int,
    val SymbolsEstimations: List<SymbolsEstimation>
)