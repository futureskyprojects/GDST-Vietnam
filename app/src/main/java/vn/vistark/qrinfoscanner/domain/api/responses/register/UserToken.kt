package vn.vistark.qrinfoscanner.domain.api.responses.register


import com.google.gson.annotations.SerializedName

data class UserToken(
    @SerializedName("headers")
    var headers: Headers = Headers(),
    @SerializedName("original")
    var original: Original = Original(),
    @SerializedName("exception")
    var exception: Any = Any()
)