package vn.vistark.qrinfoscanner.domain.api.responses.upload_qr_code


import com.google.gson.annotations.SerializedName

data class UploadQRSuccessfulResponse(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("result")
    var result: Result = Result()
)