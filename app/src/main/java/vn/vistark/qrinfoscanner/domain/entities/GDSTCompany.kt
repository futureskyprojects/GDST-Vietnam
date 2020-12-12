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
    var address: Any = Any(),
    @SerializedName("website")
    var website: Any = Any(),
    @SerializedName("logo")
    var logo: Any = Any(),
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = ""
)