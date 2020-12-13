package vn.vistark.qrinfoscanner.domain.constants

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.constants.AppStorageManager
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.domain.entities.*
import vn.vistark.qrinfoscanner.domain.mock_entities.Enterprise

class GDSTStorage {
    companion object {
        var GDSTLocations: ArrayList<GDSTLocation>? = ArrayList()
        var GDSTCountries: ArrayList<GDSTCountry>? = ArrayList()
        var GDSTSpecies: ArrayList<GDSTSpecie>? = ArrayList()
        var GDSTGearTypes: ArrayList<GDSTGearType>? = ArrayList()
        var GDSTPorts: ArrayList<GDSTPort>? = ArrayList()
        var GDSTFipCodes: ArrayList<GDSTFipCode>? = ArrayList()
        var GDSTProductForms: ArrayList<GDSTProductForm>? = ArrayList()
        var GDSTCompanies: ArrayList<GDSTCompany>? = ArrayList()

        var CurrentUser: GDSTUserProfile = GDSTUserProfile()
            get() = AppStorageManager.getObject(GDSTUserProfile::class.java.simpleName.toUpperCase() + "_USER_PROFILE")
                ?: field
            set(value) {
                AppStorageManager.updateObject(
                    GDSTUserProfile::class.java.simpleName.toUpperCase() + "_USER_PROFILE",
                    value
                )
                field = value
            }

        var CurrentCompany: GDSTCompany? = null

        fun initGDSTStatics(onSuccess: (() -> Unit)) {
            GlobalScope.launch {
                try {
                    GDSTLocations = ApiService.mAPIServices.getGDSTLocations().await()
                    GDSTCountries = ApiService.mAPIServices.getGDSTCountries().await()
                    GDSTSpecies = ApiService.mAPIServices.getGDSTSpecies().await()
                    GDSTGearTypes = ApiService.mAPIServices.getGDSTGearTypes().await()
                    GDSTPorts = ApiService.mAPIServices.getGDSTPorts().await()
                    GDSTFipCodes = ApiService.mAPIServices.getGDSTFipCodes().await()
                    GDSTProductForms = ApiService.mAPIServices.getGDSTProductForms().await()
                    GDSTCompanies = ApiService.mAPIServices.getGDSTCompanies().await()

                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }
}