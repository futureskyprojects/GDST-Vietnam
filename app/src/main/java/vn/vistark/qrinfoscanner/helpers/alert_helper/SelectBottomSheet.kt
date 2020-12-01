package vn.vistark.qrinfoscanner.helpers.alert_helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideDown
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideUp
import vn.vistark.qrinfoscanner.helpers.common.VistarkAdapter

class SelectBottomSheet {
    companion object {
        fun <T> Context.showSelectBottomSheetAlert(
            label: String,
            collection: Array<T>,
            itemLayoutId: Int,
            bindHolder: ((T, TView: View) -> View),
            onCompleted: (T?) -> Unit
        ) {

            val v = LayoutInflater.from(this).inflate(R.layout.alert_select_bottom_sheet, null)

            val asbsTvDialogLabel: TextView = v.findViewById(R.id.asbsTvDialogLabel)
            asbsTvDialogLabel.text = label

            val asbsRlMainLayout: RelativeLayout = v.findViewById(R.id.asbsRlMainLayout)
            val asbsIvCloseBtn: ImageView = v.findViewById(R.id.asbsIvCloseBtn)
            val asbsRvItems: RecyclerView = v.findViewById(R.id.asbsRvItems)

            asbsRvItems.setHasFixedSize(true)
            asbsRvItems.layoutManager = LinearLayoutManager(this)

            val adapter = VistarkAdapter(collection, itemLayoutId, bindHolder)

            asbsRvItems.adapter = adapter


            val mBuilder = AlertDialog.Builder(this)
                .setView(v)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.setCancelable(false)

            adapter.onItemClicked = { type ->
                onCompleted.invoke(type)
                asbsRlMainLayout.slideDown {
                    mAlertDialog.dismiss()
                }
            }

            asbsRlMainLayout.slideUp()

            asbsIvCloseBtn.clickAnimate {
                onCompleted.invoke(null)
                asbsRlMainLayout.slideDown {
                    mAlertDialog.dismiss()
                }
            }


        }
    }
}