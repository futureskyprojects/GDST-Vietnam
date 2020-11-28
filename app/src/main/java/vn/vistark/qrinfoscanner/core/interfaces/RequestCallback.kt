package vn.vistark.qrinfoscanner.core.interfaces

interface RequestCallback<T> {
    fun onResponse(response: T)
    fun onFailure(throwable: Throwable? = null)
}