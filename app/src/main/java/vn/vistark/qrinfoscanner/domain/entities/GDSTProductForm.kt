package vn.vistark.qrinfoscanner.domain.entities


import com.google.gson.annotations.SerializedName
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap

data class GDSTProductForm(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String = ""
) {
    fun toBaseMap(): BaseMap {
        return BaseMap(id, title)
    }

    companion object {
        fun ArrayList<GDSTProductForm>?.toBaseMap3(): Array<BaseMap> {
            val baseMaps = ArrayList<BaseMap>()
            this?.forEach {
                val baseMap = BaseMap(it.id, "${it.title}")
                baseMaps.add(baseMap)
            }
            return baseMaps.toTypedArray()
        }
    }
}