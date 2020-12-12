package vn.vistark.qrinfoscanner.domain.mock_models.fish_data.response


import com.google.gson.annotations.SerializedName

data class FishData(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("globalName")
    var globalName: String = "",
    @SerializedName("viName")
    var viName: String = "",
    @SerializedName("image")
    var image: String = ""
)