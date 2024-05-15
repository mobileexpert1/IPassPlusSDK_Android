package com.sdk.ipassplussdk.utils

object ServerUrls {
    const val base_url = "https://plusapi.ipass-mena.com/api/v1/"
//    const val base_url = "https://ipassplus.csdevhub.com/api/v1/"

    const val url_login = "ipass/create/authenticate/login"
    const val url_liveness = "ipass/plus/face/liveness"
    const val url_face_similarity = "ipass/plus/face/similarity"
    const val url_check_face_analiseaws = "ipass/plus/face/analysis"
    const val url_session_create = "ipass/plus/face/session/create"
    const val url_session_result = "ipass/plus/session/result"
    const val url_aml_manual = "ipass/plus/aml/manual"
    const val url_post_data = "ipass/plus/ocr/data"
    const val url_get_dataBy_email = "ipass/plus/ocr/data"
    const val url_data_get_sid = "plus/mobile/data/get/sid"
    const val url_valid_api = "ipass/plus/digital/manipulation"
    const val url_post_ceon = "ipass/plus/socialmedia"
    const val url_get_ceon = "ipass/plus/socialmedia/get"
    const val url_data_save = "ipass/sdk/data/save"
    const val url_id_card_details = "ipass/get/document/manipulated/result"
    const val url_facesimilarity_details = "ipass/get/liveness/facesimilarity/details"

}
