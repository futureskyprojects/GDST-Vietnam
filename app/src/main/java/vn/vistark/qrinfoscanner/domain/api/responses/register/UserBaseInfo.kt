package vn.vistark.qrinfoscanner.domain.api.responses.register


import com.google.gson.annotations.SerializedName

data class UserBaseInfo(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("token")
    var token: UserToken = UserToken()
)