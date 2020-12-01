package vn.vistark.qrinfoscanner.core.api

import retrofit2.Call
import retrofit2.http.GET
import vn.vistark.qrinfoscanner.core.models.BaseMap
import vn.vistark.qrinfoscanner.core.models.country.response.Countries
import vn.vistark.qrinfoscanner.core.models.fao.response.FAOs
import vn.vistark.qrinfoscanner.core.models.fish_data.response.FishDatas
import vn.vistark.qrinfoscanner.core.models.organization.response.Organizations
import vn.vistark.qrinfoscanner.core.models.port.response.SeaPorts
import vn.vistark.qrinfoscanner.core.models.product_form.response.ProductForm
import vn.vistark.qrinfoscanner.core.models.product_form.response.ProductForms
import vn.vistark.qrinfoscanner.core.models.unit_of_meansure.response.UnitOfMeansure
import vn.vistark.qrinfoscanner.core.models.unit_of_meansure.response.UnitOfMeansures

interface IApiService {
    @GET("countries")
    fun getAllCountries(): Call<Countries>

    @GET("gdst-fao-major-fishing-area")
    fun getAllFAOs(): Call<FAOs>

    @GET("gdst-organizations")
    fun getAllOrganizations(): Call<Organizations>

    @GET("gdst-sea-ports")
    fun getAllSeaPorts(): Call<SeaPorts>

    @GET("gdst-fip")
    fun getAllFIPs(): Call<Array<BaseMap>>

    @GET("gdst-product-method")
    fun getAllProductMethods(): Call<Array<BaseMap>>

    @GET("gdst-gear-type")
    fun getAllGearTypes(): Call<Array<BaseMap>>

    @GET("gdst-fish-data")
    fun getAllFishDatas(): Call<FishDatas>

    @GET("gdst-product-form")
    fun getAllProductForms(): Call<ProductForms>

    @GET("gdst-unit-of-meansure")
    fun getAllUnitOfMeansures(): Call<UnitOfMeansures>
}