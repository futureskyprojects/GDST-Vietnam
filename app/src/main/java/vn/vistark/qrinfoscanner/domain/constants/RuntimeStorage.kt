package vn.vistark.qrinfoscanner.domain.constants

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager
import vn.vistark.qrinfoscanner.domain.entities.GDSTUserProfile
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap
import vn.vistark.qrinfoscanner.domain.mock_models.country.response.Countries
import vn.vistark.qrinfoscanner.domain.mock_models.fao.response.FAOs
import vn.vistark.qrinfoscanner.domain.mock_models.fish_data.response.FishDatas
import vn.vistark.qrinfoscanner.domain.mock_models.organization.response.Organizations
import vn.vistark.qrinfoscanner.domain.mock_models.port.response.SeaPorts
import vn.vistark.qrinfoscanner.domain.mock_models.product_form.response.ProductForms
import vn.vistark.qrinfoscanner.domain.mock_models.unit_of_meansure.response.UnitOfMeansures

class RuntimeStorage {
    companion object {
        var Countries: Countries? = null
        var FAOs: FAOs? = null
        var Organizations: Organizations? = null
        var SeaPorts: SeaPorts? = null
        var FIPs: Array<BaseMap>? = null
        var ProductMethods: Array<BaseMap>? = null
        var GearTypes: Array<BaseMap>? = null
        var FishDatas: FishDatas? = null
        var ProductForms: ProductForms? = null
        var UnitOfMeansures: UnitOfMeansures? = null

        var CurrentUser: GDSTUserProfile? = null
            get() = AppStorageManager.getObject<GDSTUserProfile>(GDSTUserProfile::class.java.simpleName.toUpperCase() + "_USER_PROFILE")
            set(value) {
                AppStorageManager.updateObject(
                    GDSTUserProfile::class.java.simpleName.toUpperCase() + "_USER_PROFILE",
                    value
                )
                field = value
            }

        fun initStaticResources(onSuccess: (() -> Unit)) {
            GlobalScope.launch {
                try {

                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }
}