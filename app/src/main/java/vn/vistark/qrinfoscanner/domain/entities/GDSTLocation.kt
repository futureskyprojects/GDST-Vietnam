package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap

data class GDSTLocation(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String = ""
) {
    companion object {
        fun ArrayList<GDSTLocation>?.toBaseMap4(): Array<BaseMap> {
            val baseMaps = ArrayList<BaseMap>()
            this?.forEach {
                val baseMap = BaseMap(it.id, "${it.title}")
                baseMaps.add(baseMap)
            }
            return baseMaps.toTypedArray()
        }
    }

    fun toBaseMap(): BaseMap {
        return BaseMap(id, title)
    }
}