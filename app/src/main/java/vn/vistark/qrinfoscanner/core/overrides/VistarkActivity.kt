package vn.vistark.qrinfoscanner.core.overrides

import androidx.appcompat.app.AppCompatActivity
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard

abstract class VistarkActivity : AppCompatActivity() {
    override fun onUserInteraction() {
        super.onUserInteraction()
        HideKeyboard()
    }
}