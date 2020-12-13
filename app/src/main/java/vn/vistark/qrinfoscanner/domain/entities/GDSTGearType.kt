package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap

data class GDSTGearType(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String = ""
) {
    companion object {
        fun ArrayList<GDSTGearType>?.toBaseMap2(): Array<BaseMap> {
            val baseMaps = ArrayList<BaseMap>()
            this?.forEach {
                val baseMap = BaseMap(it.id, "${it.title}")
                baseMaps.add(baseMap)
            }
            return baseMaps.toTypedArray()
        }
    }
}