package vn.vistark.qrinfoscanner.core.extensions

import vn.vistark.qrinfoscanner.core.constants.RuntimeStorage

class Authentication {
    companion object {
        fun isAuthenticated(): Boolean {
            return RuntimeStorage.CurrentEnterprise != null &&
                    RuntimeStorage.CurrentEnterprise!!.identity.isNotEmpty() &&
                    RuntimeStorage.CurrentEnterprise!!.hashPassword.isNotEmpty()
        }
    }
}