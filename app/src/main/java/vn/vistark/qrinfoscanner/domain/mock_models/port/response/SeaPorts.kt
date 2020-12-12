package vn.vistark.qrinfoscanner.domain.mock_models.port.response

import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap


class SeaPorts : ArrayList<SeaPort>() {
    fun toBaseMaps(): Array<BaseMap> {
        val baseMaps = ArrayList<BaseMap>()
        forEach {
            val baseMap = BaseMap(it.id, "${it.name} - ${it.province} (${it.country})")
            baseMaps.add(baseMap)
        }
        return baseMaps.toTypedArray()
    }
}