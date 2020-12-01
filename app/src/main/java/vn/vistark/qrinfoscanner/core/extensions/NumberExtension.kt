package vn.vistark.qrinfoscanner.core.extensions

class NumberExtension {
    companion object {
        fun Double.round(place: Int = 2): Double {
            return String.format("%.${place}f", this).toDouble()
        }
    }
}