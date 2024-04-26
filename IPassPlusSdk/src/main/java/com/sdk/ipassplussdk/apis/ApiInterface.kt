package com.sdk.ipassplussdk.apis

import com.sdk.ipassplussdk.model.request.aml_manual.AmlManualRequest
import com.sdk.ipassplussdk.model.request.ceon.GetCeon.GetCeonRequest
import com.sdk.ipassplussdk.model.request.ceon.PostCeon.PostCeonRequest
import com.sdk.ipassplussdk.model.request.check_face_analysis.CheckFaceAnalysisRequest
import com.sdk.ipassplussdk.model.request.facesimilarity.FaceSimilarityRequest
import com.sdk.ipassplussdk.model.request.livenesscheck.LivenessCheckRequest
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.request.ipass.ipass_get_data_by_email.OcrGetDataByEmailRequest
import com.sdk.ipassplussdk.model.request.ipass.ipass_post_data.OcrPostdataRequest
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.request.session_result.SessionResultRequest
import com.sdk.ipassplussdk.model.request.valid_api.ValidApiRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.aml_manual.AmlManualResponse
import com.sdk.ipassplussdk.model.response.ceon.GetCeon.GetCeonResponse
import com.sdk.ipassplussdk.model.response.ceon.PostCeon.PostCeonResponse
import com.sdk.ipassplussdk.model.response.check_face_analysis.CheckFaceAnalysisResponse
import com.sdk.ipassplussdk.model.response.data_save.DataSaveRequest
import com.sdk.ipassplussdk.model.response.data_save.DataSaveResponse
import com.sdk.ipassplussdk.model.response.data_save.Livenessdata
import com.sdk.ipassplussdk.model.response.document_scanner.DocumentScannerResponse
import com.sdk.ipassplussdk.model.response.livenesscheck.LivenessCheckResponse
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.model.response.ipass.ipass_data_get_sid.IpassDataGetSidResponse
import com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email.OcrGetDataByEmailResponse
import com.sdk.ipassplussdk.model.response.ipass.ipass_post_data.OcrPostDataResponse
import com.sdk.ipassplussdk.model.response.liveness_facesimilarity.FaceScannerResponse
import com.sdk.ipassplussdk.model.response.session_create.Data
import com.sdk.ipassplussdk.model.response.valid_api.ValidApiResponse
import com.sdk.ipassplussdk.utils.ServerUrls
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for endpoint methods
 */
interface ApiInterface {

    @POST(ServerUrls.url_login)
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST(ServerUrls.url_liveness)
    fun livenessCheck(
        @Query("token") token: String,
        @Query("sessionId") sessionId: String,
        @Query("sid") sid: String,
        @Query("email") email: String,
        @Body livenessCheckRequest: LivenessCheckRequest
    ): Call<LivenessCheckResponse>

    @POST(ServerUrls.url_face_similarity)
    @Headers("Content-Type: application/json")
    fun facesimilarity(
        @Query("token") token: String,
        @Body faceSimilarityRequest: FaceSimilarityRequest
    ): Call<com.sdk.ipassplussdk.model.response.facesimilarity.Data>

    @POST(ServerUrls.url_check_face_analiseaws)
    fun checkFaceAnaliseaws(
        @Query("token") token: String,
        @Query("token1") token1: String,
        @Body checkFaceAnalysisRequest: CheckFaceAnalysisRequest
    ): Call<BaseModel<CheckFaceAnalysisResponse>>

    @POST(ServerUrls.url_session_create)
    fun sessionCreate(
        @Query("token") token: String,
        @Body sessionCreateRequest: SessionCreateRequest
//        @Query("token1") token1: String,
    ): Call<Data>

    @POST(ServerUrls.url_session_result)
    fun sessionResult(
        @Query("token") token: String,
        @Query("sessionId") sessionId: String,
        @Query("sid") sid: String,
        @Query("email") email: String,
        @Body sessionCreateRequest: SessionResultRequest
//        @Query("token1") token1: String,
    ): Call<Livenessdata>

    @POST(ServerUrls.url_aml_manual)
    fun amlManual(
        @Query("token") token: String,
        @Body amlManualRequest: AmlManualRequest
    ): Call<AmlManualResponse>

    @POST(ServerUrls.url_regula_post_data)
    fun ocrPostData(
        @Query("token") token: String,
//        @Query("token1") token1: String,
        @Body ocrPostdataRequest: OcrPostdataRequest
    ): Call<OcrPostDataResponse>

    @POST(ServerUrls.url_regula_get_dataBy_email)
    fun ocrGetDataBy(
        @Query("token") token: String,
        @Query("token1") token1: String,
        @Body ocrGetDataByEmailRequest: OcrGetDataByEmailRequest
    ): Call<BaseModel<OcrGetDataByEmailResponse>>

    @GET(ServerUrls.url_regula_data_get_sid)
    fun dataGetSid(
        @Query("token") token: String,
        @Query("token1") token1: String,
        @Query("sid") sid: String
    ): Call<BaseModel<IpassDataGetSidResponse>>

    @POST(ServerUrls.url_valid_api)
    fun validApi(
        @Query("token") token: String,
        @Body validApiRequest: ValidApiRequest
    ): Call<ValidApiResponse>

    @POST(ServerUrls.url_post_ceon)
    fun postCeonData(
        @Query("token") token: String,
        @Query("token1") token1: String,
        @Body postCeonRequest: PostCeonRequest
    ): Call<BaseModel<PostCeonResponse>>

    @POST(ServerUrls.url_get_ceon)
    fun getCeonData(
        @Query("token") token: String,
        @Query("token1") token1: String,
        @Body getCeonRequest: GetCeonRequest
    ): Call<BaseModel<GetCeonResponse>>

    @POST(ServerUrls.url_data_save)
    fun dataSaveRequest(
        @Body getCeonRequest: DataSaveRequest
    ): Call<DataSaveResponse>

    @GET(ServerUrls.url_id_card_details)
    fun getDocumentScannerData(
        @Query("token") token: String,
        @Query("sesid") sessId: String
    ): Call<DocumentScannerResponse>

    @GET(ServerUrls.url_facesimilarity_details)
    fun getLivenessFaceSimilarityData(
        @Query("token") token: String,
        @Query("sessId") sessId: String
    ): Call<FaceScannerResponse>

}
