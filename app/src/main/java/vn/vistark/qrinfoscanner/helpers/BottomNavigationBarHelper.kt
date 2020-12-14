package vn.vistark.qrinfoscanner.helpers

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideDown
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.slideUp
import vn.vistark.qrinfoscanner.ui.account_info.AccountInfoActivity
import vn.vistark.qrinfoscanner.ui.home.HomeActivity
import vn.vistark.qrinfoscanner.ui.qr_result_processing.QRResultProcessingActivity


class BottomNavigationBarHelper {
    companion object {
        fun View.initGDSTSmartBottomBar(scrv: ScrollView) {
            var state = ""
            scrv.viewTreeObserver
                .addOnScrollChangedListener(object : OnScrollChangedListener {
                    var y = 0
                    override fun onScrollChanged() {
                        if (scrv.scrollY > y) {
                            // Scrolling down
                            if (state != "DOWN") {
                                slideUp()
                                state = "DOWN"
                            }
                        } else {
                            // Scrolling up
                            if (state != "UP") {
                                slideDown()
                                state = "UP"
                            }
                        }
                        y = scrv.scrollY
                    }
                })
        }

        fun View.initGDSTSmartBottomBar(rv: RecyclerView) {
            var state = ""
            rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        // Scrolling up
                        if (state != "UP") {
                            slideDown()
                            state = "UP"
                        }
                    } else {
                        // Scrolling down
                        if (state != "DOWN") {
                            slideUp()
                            state = "DOWN"
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                            // Do something
                        }
                        AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {
                            // Do something
                        }
                        else -> {
                            // Do something
                        }
                    }
                }
            })
        }

        fun Context.initGDSTBottomBar(
            bottomNav: BottomNavigationView,
            centerBtn: FloatingActionButton,
            selectedId: Int = 1
        ) {

            bottomNav.selectedItemId =
                if (selectedId == 1) R.id.home else if (selectedId == 2) R.id.profile else -1

            bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_home -> {
                        if (selectedId != 1) {
                            val intent = Intent(
                                this,
                                HomeActivity::class.java
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.profile -> {
                        if (selectedId != 2) {
                            val intent = Intent(
                                this,
                                AccountInfoActivity::class.java
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })

            Glide.with(this).asGif().load(R.raw.scan_qr_gif)
                .transform(CircleCrop()) // or bitmapTransform, whichever compiles
                .into(centerBtn)

            centerBtn.clickAnimate {
                val intent = Intent(this, QRResultProcessingActivity::class.java)
                startActivity(intent)
            }
        }

    }
}