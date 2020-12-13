package vn.vistark.qrinfoscanner.core.extensions

import vn.vistark.qrinfoscanner.core.api.AuthIntercepter
import vn.vistark.qrinfoscanner.domain.constants.RuntimeStorage

class Authentication {
    companion object {
        fun isAuthenticated(): Boolean {
            return AuthIntercepter.CurrentToken.isNotEmpty() &&
                    AuthIntercepter.CurrentTokenType.isNotEmpty() &&
                    AuthIntercepter.AuthorizationKey.isNotEmpty()
        }
    }
}