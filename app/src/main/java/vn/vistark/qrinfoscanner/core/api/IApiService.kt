package vn.vistark.qrinfoscanner.core.api

import retrofit2.Call
import retrofit2.http.GET
import vn.vistark.qrinfoscanner.core.models.BaseMap
import vn.vistark.qrinfoscanner.core.models.country.response.Countries
import vn.vistark.qrinfoscanner.core.models.fao.response.FAOs
import vn.vistark.qrinfoscanner.core.models.organization.response.Organizations
import vn.vistark.qrinfoscanner.core.models.port.response.SeaPorts

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
}