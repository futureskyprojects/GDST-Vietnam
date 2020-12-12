package vn.vistark.qrinfoscanner.core.api

import vn.vistark.qrinfoscanner.domain.api.IApiService

class ApiService {
    companion object {
        val mAPIServices: IApiService
            get() {
                return RetrofitClient.client.create(IApiService::class.java)
            }
    }
}