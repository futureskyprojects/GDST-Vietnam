package vn.vistark.qrinfoscanner.domain.api.responses.register


import com.google.gson.annotations.SerializedName

data class Original(
    @SerializedName("access_token")
    var accessToken: String = "",
    @SerializedName("token_type")
    var tokenType: String = "",
    @SerializedName("expires_in")
    var expiresIn: Int = 0
)