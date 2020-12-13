package vn.vistark.qrinfoscanner.domain.api.responses.account


import com.google.gson.annotations.SerializedName

data class AccountSuccessfulRespone(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("username")
    var username: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("fullname")
    var fullname: String? = "",
    @SerializedName("image")
    var image: String? = "",
    @SerializedName("company_id")
    var companyId: Int? = 0,
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("updated_at")
    var updatedAt: String? = ""
)