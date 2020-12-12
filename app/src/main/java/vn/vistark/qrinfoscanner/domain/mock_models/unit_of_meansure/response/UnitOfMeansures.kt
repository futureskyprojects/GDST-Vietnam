package vn.vistark.qrinfoscanner.domain.mock_models.unit_of_meansure.response

import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap


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