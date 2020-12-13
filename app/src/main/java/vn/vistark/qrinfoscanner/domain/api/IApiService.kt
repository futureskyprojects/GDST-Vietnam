package vn.vistark.qrinfoscanner.domain.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import vn.vistark.qrinfoscanner.domain.DTOs.*
import vn.vistark.qrinfoscanner.domain.api.responses.account.AccountSuccessfulRespone
import vn.vistark.qrinfoscanner.domain.api.responses.login.LoginSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.api.responses.material_batch.MaterialBatchCreateSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.api.responses.material_ship.CreateMaterialShipSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.api.responses.register.RegisterSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.api.responses.shipment.ShipmentCreateSuccessfulResponse
import vn.vistark.qrinfoscanner.domain.api.responses.upload_qr_code.UploadQRSuccessfulResponse
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

    @GET("api/account")
    fun getGDSTProfile(): Call<AccountSuccessfulRespone>

    @GET("api/ships")
    fun getGDSTShip(): Call<ArrayList<GDSTShip>>

    @GET("api/shipments")
    fun getGDSTShipment(): Call<ArrayList<GDSTShipment>>

    @Multipart
    @POST("api/upload/qrcode")
    fun postGDSTUploadQrCode(@Part image: MultipartBody.Part): Call<UploadQRSuccessfulResponse>

    @POST("api/crate-shipment")
    fun postGDSTShipment(@Body dto: GDSTShipmentCreateDTO): Call<ShipmentCreateSuccessfulResponse>

    @GET("api/material-bacths")
    fun getGDSTMaterialBatch(): Call<ArrayList<GDSTMaterialBacth>>

    @POST("api/crate-merterial-batch")
    fun postGDSTMaterialBatch(@Body dto: GDSTMaterialBacthCreateDTO): Call<MaterialBatchCreateSuccessfulResponse>

    @GET("api/material-ships")
    fun getGDSTMaterialShip(): Call<ArrayList<GDSTMaterialShip>>

    @POST("api/crate-merterial-ship")
    fun postGDSTMaterialShip(@Body dto: GDSTMaterialShipCreateDTO): Call<CreateMaterialShipSuccessfulResponse>
}