package com.sdk.ipassplussdk.apis

import okhttp3.Request

// Build method to add api headers
fun build(request: Request/*, EXiGlobalHeader: EXiGlobalHeader*/): Request.Builder {
    return request.newBuilder()
//        .addHeader(Constants.headers.HEADERNAME_XPIKEY, EXiGlobalHeader.xApiKeyValue)
//        .addHeader(Constants.headers.HEADERNAME_JWS, EXiGlobalHeader.jwsKeyValue)
}
