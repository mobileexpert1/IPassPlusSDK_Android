package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class Datat (
    val DocGraphicsInfo: DocGraphicsInfo,
    val DocVisualExtendedInfo: DocVisualExtendedInfo,
    val DocumentPosition: DocumentPosition,
    val FaceDetection: FaceDetection,
    val ImageQualityCheckList: ImageQualityCheckList,
    val Images: Images,
    val ListVerifiedFields: ListVerifiedFields,
    val MRZTestQuality: MRZTestQuality,
    val MrzPosition: MrzPosition,
    val OneCandidate: OneCandidate,
    val ResultMRZDetector: ResultMRZDetector,
    val Status: Status,
    val Text: Text,
    val buf_length: Int,
    val light: Int,
    val list_idx: Int,
    val page_idx: Int,
    val result_type: Int
)