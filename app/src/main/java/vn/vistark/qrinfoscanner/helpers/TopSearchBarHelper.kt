package vn.vistark.qrinfoscanner.helpers

import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.AbsListView
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import vn.vistark.qrinfoscanner.core.extensions.StringExtension.Companion.isSameWith
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.onChanged
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideDownShow
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideUpHide
import java.util.*
import kotlin.collections.ArrayList


class TopSearchBarHelper {
    companion object {
        fun View.initGDSTSmartTopSearchBar(rv: RecyclerView) {
//            var state = ""
//            rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if (dy > 0) {
//                        // Scrolling up
//                        if (state != "UP") {
//                            slideUpHide()
//                            state = "UP"
//                        }
//                    } else {
//                        // Scrolling down
//                        if (state != "DOWN") {
//                            slideDownShow()
//                            state = "DOWN"
//                        }
//                    }
//                }
//
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    when (newState) {
//                        AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
//                            // Do something
//                        }
//                        AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {
//                            // Do something
//                        }
//                        else -> {
//                            // Do something
//                        }
//                    }
//                }
//            })
        }

        fun <T> EditText.initGDSTTopSearchBar(
            arr: ArrayList<T>,
            onResult: (ArrayList<T>) -> Unit
        ) {
            val defaultArr = ArrayList<T>()
            defaultArr.addAll(arr)
            onChanged {
                val s = text.toString()
                val newArr = ArrayList<T>()
                defaultArr.forEach { t ->
                    val json = Gson().toJson(t)
                    if (s.isEmpty() || json.toLowerCase(Locale.getDefault())
                            .contains(s.toLowerCase(Locale.getDefault()))
                    ) {
                        newArr.add(t)
                    }
                }
                onResult.invoke(newArr)
            }
        }

    }
}