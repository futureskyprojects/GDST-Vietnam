package vn.vistark.qrinfoscanner.core.models.unit_of_meansure.response


import com.google.gson.annotations.SerializedName

data class UnitOfMeansure(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("code")
    var code: String = ""
)