package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap

data class GDSTFipCode(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("status")
    var status: Int = 0
) {
    companion object {
        fun ArrayList<GDSTFipCode>?.toBaseMap1(): Array<BaseMap> {
            val baseMaps = ArrayList<BaseMap>()
            this?.forEach {
                val baseMap = BaseMap(it.id, "${it.title}")
                baseMaps.add(baseMap)
            }
            return baseMaps.toTypedArray()
        }
    }
}