package vn.vistark.qrinfoscanner.domain.api.responses.upload_qr_code


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("path")
    var path: String = ""
)