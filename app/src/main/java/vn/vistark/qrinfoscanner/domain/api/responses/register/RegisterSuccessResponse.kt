package vn.vistark.qrinfoscanner.domain.api.responses.register


import com.google.gson.annotations.SerializedName

data class RegisterSuccessResponse(
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("message")
    var message: String = "",
    @SerializedName("data")
    var `data`: UserBaseInfo = UserBaseInfo()
)