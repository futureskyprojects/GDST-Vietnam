package vn.vistark.qrinfoscanner.core.constants

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.entities.Enterprise
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.models.BaseMap
import vn.vistark.qrinfoscanner.core.models.country.response.Countries
import vn.vistark.qrinfoscanner.core.models.fao.response.FAOs
import vn.vistark.qrinfoscanner.core.models.fish_data.response.FishDatas
import vn.vistark.qrinfoscanner.core.models.organization.response.Organizations
import vn.vistark.qrinfoscanner.core.models.port.response.SeaPorts
import vn.vistark.qrinfoscanner.core.models.product_form.response.ProductForm
import vn.vistark.qrinfoscanner.core.models.product_form.response.ProductForms
import vn.vistark.qrinfoscanner.core.models.unit_of_meansure.response.UnitOfMeansures

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

        var CurrentEnterprise: Enterprise? = null
            get() = AppStorageManager.getObject<Enterprise>(Enterprise::class.java.simpleName + "Account")
            set(value) {
                AppStorageManager.update(Enterprise::class.java.simpleName + "Account", value)
                field = value
            }

        fun initStaticResources(onSuccess: (() -> Unit)) {
            GlobalScope.launch {
                try {
                    // Lấy danh sách các quốc gia
                    Countries = ApiService.mAPIServices.getAllCountries().await()

                    // Lấy danh sách FAOs
                    FAOs = ApiService.mAPIServices.getAllFAOs().await()

                    // Lấy danh sách các tổ chức
                    Organizations =
                        ApiService.mAPIServices.getAllOrganizations().await()

                    // Lấy danh sách các cảng biển
                    SeaPorts = ApiService.mAPIServices.getAllSeaPorts().await()

                    // Lấy danh sách các loài cá
                    FishDatas = ApiService.mAPIServices.getAllFishDatas().await()

                    // Lấy danh sách dạng sản phẩm
                    ProductForms = ApiService.mAPIServices.getAllProductForms().await()

                    // lấy danh sách đơn vị tính
                    UnitOfMeansures = ApiService.mAPIServices.getAllUnitOfMeansures().await()

                    FIPs = ApiService.mAPIServices.getAllFIPs().await()
                    ProductMethods = ApiService.mAPIServices.getAllProductMethods().await()
                    GearTypes = ApiService.mAPIServices.getAllGearTypes().await()

                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }
}