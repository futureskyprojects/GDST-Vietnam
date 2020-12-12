package vn.vistark.qrinfoscanner.domain.api

import retrofit2.Call
import retrofit2.http.GET
import vn.vistark.qrinfoscanner.domain.api.responses.GDSTCompanies
import vn.vistark.qrinfoscanner.domain.entities.*
import vn.vistark.qrinfoscanner.domain.mock_models.BaseMap
import vn.vistark.qrinfoscanner.domain.mock_models.country.response.Countries
import vn.vistark.qrinfoscanner.domain.mock_models.fao.response.FAOs
import vn.vistark.qrinfoscanner.domain.mock_models.fish_data.response.FishDatas
import vn.vistark.qrinfoscanner.domain.mock_models.organization.response.Organizations
import vn.vistark.qrinfoscanner.domain.mock_models.port.response.SeaPorts
import vn.vistark.qrinfoscanner.domain.mock_models.product_form.response.ProductForms
import vn.vistark.qrinfoscanner.domain.mock_models.unit_of_meansure.response.UnitOfMeansures

interface IApiService {
    companion object {
        const val BASE_URL = "http://gdst.ga/"
    }

    @GET("api/locations")
    fun getGDSTLocations(): Call<ArrayList<GDSTLocation>>

    @GET("api/countries")
    fun getGDSTCountries(): Call<ArrayList<GDSTCountry>>

    @GET("api/species")
    fun getGDSTSpecies(): Call<ArrayList<GDSTSpecie>>

    @GET("api/gear-types")
    fun getGDSTGearTypes(): Call<ArrayList<GDSTGearType>>

    @GET("api/ports")
    fun getGDSTPorts(): Call<ArrayList<GDSTPort>>

    @GET("api/fipcodes")
    fun getGDSTFipCodes(): Call<ArrayList<GDSTFipCode>>

    @GET("api/product-from")
    fun getGDSTProductForms(): Call<ArrayList<GDSTProductForm>>

    @GET("api/companies")
    fun getGDSTCompanies(): Call<GDSTCompanies>
}