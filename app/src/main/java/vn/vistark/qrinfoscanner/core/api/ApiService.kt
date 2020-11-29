package vn.vistark.qrinfoscanner.core.api

class ApiService {
    companion object {
        val mAPIServices: IApiService
            get() {
                return RetrofitClient.client.create(IApiService::class.java)
            }
    }
}