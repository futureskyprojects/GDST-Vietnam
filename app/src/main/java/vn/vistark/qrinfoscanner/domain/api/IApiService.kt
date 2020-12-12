package vn.vistark.qrinfoscanner.domain.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTUserLoginDTO
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTUserRegisterDTO
import vn.vistark.qrinfoscanner.domain.api.responses.login.LoginSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.api.responses.register.RegisterSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.entities.*

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

    @GET("api/product-froms")
    fun getGDSTProductForms(): Call<ArrayList<GDSTProductForm>>

    @GET("api/companies")
    fun getGDSTCompanies(): Call<ArrayList<GDSTCompany>>

    @POST("api/register")
    fun postGDSTRegister(@Body dto: GDSTUserRegisterDTO): Call<RegisterSuccessfulResponse>

    @POST("api/login")
    fun postGDSTLogin(@Body dto: GDSTUserLoginDTO): Call<LoginSuccessfulResponse>
}