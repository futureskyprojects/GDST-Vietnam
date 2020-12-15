package vn.vistark.qrinfoscanner.core.extensions

class StringExtension {
    companion object {
        fun String.isSameWith(s: String): Boolean {
            return (contains(s) || s.contains(this) || s == this)
        }
    }
}