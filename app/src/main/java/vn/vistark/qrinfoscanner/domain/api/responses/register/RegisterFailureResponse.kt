package vn.vistark.qrinfoscanner.domain.api.responses.register


import com.google.gson.annotations.SerializedName

data class RegisterFailureResponse(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = ""
)