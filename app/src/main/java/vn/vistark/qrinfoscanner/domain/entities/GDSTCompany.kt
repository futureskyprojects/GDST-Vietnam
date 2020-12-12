package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName

data class GDSTCompany(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("taxcode")
    var taxcode: String = "",
    @SerializedName("companyname")
    var companyname: String = "",
    @SerializedName("ownername")
    var ownername: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("website")
    var website: String = "",
    @SerializedName("logo")
    var logo: String = "",
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = ""
)