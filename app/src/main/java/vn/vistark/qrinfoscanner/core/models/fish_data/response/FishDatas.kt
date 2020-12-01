package vn.vistark.qrinfoscanner.core.models.fish_data.response

import vn.vistark.qrinfoscanner.core.models.BaseMap


class FishDatas : ArrayList<FishData>() {
    fun toBaseMaps(): Array<BaseMap> {
        val baseMaps = ArrayList<BaseMap>()
        forEach {
            val baseMap = BaseMap(it.id, "${it.viName} (${it.globalName})")
            baseMaps.add(baseMap)
        }
        return baseMaps.toTypedArray()
    }

    fun fromBaseMap(baseMap: BaseMap?): FishData? {
        forEach {
            if (it.id == baseMap?.id)
                return it
        }
        return null
    }
}