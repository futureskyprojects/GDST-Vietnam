package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.api.IApiService

data class GDSTSpecie(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("image")
    var image: String? = ""
) {
    fun TrueImage(): String {
        val temp = image?.replace("^/".toRegex(), "") ?: ""
        return (IApiService.BASE_URL + temp)
    }
}