package vn.vistark.qrinfoscanner.domain.mock_models.fao.response


import com.google.gson.annotations.SerializedName

data class FAO(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mapImages")
    var mapImages: String = ""
)