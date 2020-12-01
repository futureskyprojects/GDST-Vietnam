package vn.vistark.qrinfoscanner.core.models.unit_of_meansure.response

import vn.vistark.qrinfoscanner.core.models.BaseMap


class UnitOfMeansures : ArrayList<UnitOfMeansure>() {
    fun toBaseMaps(): Array<BaseMap> {
        val baseMaps = ArrayList<BaseMap>()
        forEach {
            val baseMap = BaseMap(it.id, it.code)
            baseMaps.add(baseMap)
        }
        return baseMaps.toTypedArray()
    }
}