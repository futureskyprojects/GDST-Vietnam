package vn.vistark.qrinfoscanner.core.models.product_form.response

import vn.vistark.qrinfoscanner.core.models.BaseMap


class ProductForms : ArrayList<ProductForm>() {
    fun toBaseMaps(): Array<BaseMap> {
        val baseMaps = ArrayList<BaseMap>()
        forEach {
            val baseMap = BaseMap(it.id, it.code)
            baseMaps.add(baseMap)
        }
        return baseMaps.toTypedArray()
    }
}