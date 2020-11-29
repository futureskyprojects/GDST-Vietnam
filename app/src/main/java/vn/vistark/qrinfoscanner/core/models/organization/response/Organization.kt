package vn.vistark.qrinfoscanner.core.models.organization.response


import com.google.gson.annotations.SerializedName

data class Organization(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
)