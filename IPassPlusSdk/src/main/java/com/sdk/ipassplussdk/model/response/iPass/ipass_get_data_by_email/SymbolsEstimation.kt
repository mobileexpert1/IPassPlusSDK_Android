package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class SymbolsEstimation(
    val ALIGNMENT_NEAREST_SYMBOLS: Int,
    val CONTRAST_PRINT: Int,
    val CONTRAST_SYMBOL: Int,
    val CharSymbol: Int,
    val EDGE: Int,
    val EMPTINESS: Int,
    val STAIN: Int,
    val SYMBOLS_INTERVAL: Int,
    val SYMBOL_PARAM: Int,
    val SYMBOL_SIZE: Int,
    val SizeErrorAlignWithNext: Int,
    val SizeErrorAlignWithPrev: Int,
    val SizeErrorIntervWithNext: Int,
    val SizeErrorIntervWithPrev: Int,
    val SizeErrorSymbolHeight: Int,
    val SizeErrorSymbolWidth: Int,
    val SymbolBounds: SymbolBounds
)