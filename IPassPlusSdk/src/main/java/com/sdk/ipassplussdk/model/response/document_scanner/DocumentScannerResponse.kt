package com.sdk.ipassplussdk.model.response.document_scanner

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DocumentScannerResponse (


  @SerializedName("Apistatus"  ) var Apistatus  : Boolean? = null,
  @SerializedName("Apimessage" ) var Apimessage : String?  = null,
  @SerializedName("data"       ) var data       : Data?    = Data()

)


data class Data (

  @SerializedName("OverAllStatus"        ) var OverAllStatus        : String?                         = null,
  @SerializedName("Reason"               ) var Reason               : ArrayList<Reason>               = arrayListOf(),
  @SerializedName("DocDetails"           ) var DocDetails           : DocDetails?                     = DocDetails(),
  @SerializedName("DocImages"            ) var DocImages            : DocImages?                      = DocImages(),
  @SerializedName("livenessResult"       ) var livenessResult       : ArrayList<LivenessResult>       = arrayListOf(),
  @SerializedName("faceComparisonResult" ) var faceComparisonResult : ArrayList<FaceComparisonResult> = arrayListOf()

)


data class FaceComparisonResult (

  @SerializedName("_id"               ) var Id                : String? = null,
  @SerializedName("sourceImageBase64" ) var sourceImageBase64 : String? = null,
  @SerializedName("targetImageBase64" ) var targetImageBase64 : String? = null,
  @SerializedName("facePercentage"    ) var facePercentage    : Double?    = null,
  @SerializedName("timestamp"         ) var timestamp         : String? = null,
  @SerializedName("email"             ) var email             : String? = null,
  @SerializedName("sid"               ) var sid               : String? = null,
  @SerializedName("status"            ) var status            : String? = null,
  @SerializedName("confidence"        ) var confidence        : String? = null,
  @SerializedName("__v"               ) var _v                : Int?    = null

)

data class LivenessResult (

  @SerializedName("_id"      ) var Id       : String?   = null,
  @SerializedName("response" ) var response : Response? = Response(),
  @SerializedName("sid"      ) var sid      : String?   = null,
  @SerializedName("email"    ) var email    : String?   = null,
  @SerializedName("__v"      ) var _v       : Int?      = null

)
data class Response (

  @SerializedName("AuditImages"    ) var AuditImages    : ArrayList<String> = arrayListOf(),
  @SerializedName("Confidence"     ) var Confidence     : Double?           = null,
  @SerializedName("ReferenceImage" ) var ReferenceImage : ReferenceImage?   = ReferenceImage(),
  @SerializedName("SessionId"      ) var SessionId      : String?           = null,
  @SerializedName("Status"         ) var Status         : String?           = null,
  @SerializedName("metadata"       ) var metadata       : Metadata?         = Metadata()

)

data class Metadata (

  @SerializedName("httpStatusCode"  ) var httpStatusCode  : Int?    = null,
  @SerializedName("requestId"       ) var requestId       : String? = null,
  @SerializedName("attempts"        ) var attempts        : Int?    = null,
  @SerializedName("totalRetryDelay" ) var totalRetryDelay : Int?    = null

)

data class ReferenceImage (

  @SerializedName("BoundingBox" ) var BoundingBox : BoundingBox? = BoundingBox(),
  @SerializedName("Bytes"       ) var Bytes       : String?      = null

)

data class BoundingBox (

  @SerializedName("Height" ) var Height : Double? = null,
  @SerializedName("Left"   ) var Left   : Double? = null,
  @SerializedName("Top"    ) var Top    : Double? = null,
  @SerializedName("Width"  ) var Width  : Double? = null

)

data class DocImages (

  @SerializedName("Portrait"          ) var Portrait          : String? = null,
  @SerializedName("Ghostportrait"     ) var Ghostportrait     : String? = null,
  @SerializedName("Signature"         ) var Signature         : String? = null,
  @SerializedName("documentFrontSide" ) var documentFrontSide : String? = null

)


data class DocDetails (

  @SerializedName("Age"           )           var             Age           :           String?    =        null,
  @SerializedName("AddressAr"     )           var             AddressAr     :           String?    =        null,
  @SerializedName("Surname And Given Names"        )           var        Surname_And_Given_Names      : String? = null,
  @SerializedName("Date of Birth"          )             var         Date_of_Birth          :      String?    = null,
  @SerializedName("Document Number Checkdigit"     )             var         Document_Number_Checkdigit     :      String?    = null,
  @SerializedName("Document Number"     )               var           Document_Number     :        String?        =      null,
  @SerializedName("Date of Birth Checkdigit"   )           var        Date_of_Birth_Checkdigit : String? = null,
  @SerializedName("Sex"           )           var             Sex           :           String?    =        null,
  @SerializedName("Date of Expiry"         )             var         Date_of_Expiry         :      String?    = null,
  @SerializedName("Date of Expiry Checkdigit"   )           var        Date_of_Expiry_Checkdigit : String? = null,
  @SerializedName("Nationality Code"       )               var           Nationality_Code       :        String?        =      null,
  @SerializedName("Final Checkdigit" )               var           Final_Checkdigit :        String?        =      null,
  @SerializedName("Issuing State Code"           )             var         Issuing_State_Code           :      String?    = null,
  @SerializedName("RemainderTerm" )           var             RemainderTerm :           String?    =        null,
  @SerializedName("Document Class Code"           )             var         Document_Class_Code           :      String?    = null,
  @SerializedName("MRZ Strings"    )               var           MRZ_Strings    :        String?        =      null,
  @SerializedName("Nationality"   )           var             Nationality   :           String?    =        null,
  @SerializedName("Issuing State Name"           )             var         Issuing_State_Name           :      String?    = null,
  @SerializedName("Optional Data"       )               var           Optional_Data       :        String?        =      null,
  @SerializedName("Line2 Optional Data"           )             var         Line2_Optional_Data           :      String?    = null,
  @SerializedName("MRZ Type"       )               var           MRZ_Type       :        String?        =      null,
  @SerializedName("Given Names"      )               var           Given_Names      :        String?        =      null,
  @SerializedName("SexAr"         )           var             SexAr         :           String?    =        null,
  @SerializedName("Place of BirthAr"        )             var         Place_of_BirthAr        :      String?    = null,
  @SerializedName("Surname And Given NamesAr"      )           var        Surname_And_Given_NamesAr    : String? = null,
  @SerializedName("Mothers NameAr"     )               var           Mothers_NameAr     :        String?        =      null,
  @SerializedName("Blood Group"      )               var           Blood_Group      :        String?        =      null,
  @SerializedName("Place of IssueAr"        )             var         Place_of_IssueAr        :      String?    = null,
  @SerializedName("Place of RegistrationAr" )             var         Place_of_RegistrationAr :      String?    = null,
  @SerializedName("Place of Birth"          )             var         Place_of_Birth          :      String?    = null,
  @SerializedName("Mothers Name"       )               var           Mothers_Name       :        String?        =      null,
  @SerializedName("Surname"       )           var             Surname       :           String?    =        null

)

data class Reason (

  @SerializedName("Status" ) var Status : String? = null,
  @SerializedName("Text"   ) var Text   : String? = null

)