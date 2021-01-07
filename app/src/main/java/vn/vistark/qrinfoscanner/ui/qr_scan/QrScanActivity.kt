package vn.vistark.qrinfoscanner.ui.qr_scan

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_qr_scan.*
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.components.CustomViewFinderView
import vn.vistark.qrinfoscanner.core.helpers.DimensionHelper
import vn.vistark.qrinfoscanner.core.helpers.VistarkContextWrapper
import vn.vistark.qrinfoscanner.core.overrides.VistarkActivity
import vn.vistark.qrinfoscanner.domain.constants.Config


class QrScanActivity : VistarkActivity() {

//    private var dl: DrawerLayout? = null
//    private var t: ActionBarDrawerToggle? = null
//    private var nv: NavigationView? = null
//
//    private lateinit var navMenuItemAccountInfo: RelativeLayout
//    private lateinit var navMenuItemPasswordUpdate: RelativeLayout
//    private lateinit var navMenuItemLogout: RelativeLayout
//    private lateinit var navCloseBtn: ImageView

    private lateinit var capture: CaptureManager
    var listener: OnGlobalLayoutListener? = null
    private lateinit var zxingViewfinderView: CustomViewFinderView
    private lateinit var zxingStatusView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)



        capture = CaptureManager(this, bcScanner)
        capture.apply {
            initializeFromIntent(intent, savedInstanceState)
            decode()
        }

        initViews()
        setUpZxingStatusViewPosition()
//        updateToolbarPosition()
//        initNavigationDrawer()

        initEvents()
    }


//    private fun initNavigationDrawer() {
//        dl = findViewById(R.id.activity_qr_scan)
//        t = ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close)
//
//        dl!!.addDrawerListener(t!!)
//        t!!.syncState()
//
////        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
//        nv = findViewById(R.id.nv)
//        navMenuItemAccountInfo = nv!!.findViewById(R.id.navMenuItemAccountInfo)
//        navMenuItemPasswordUpdate = nv!!.findViewById(R.id.navMenuItemPasswordUpdate)
//        navMenuItemLogout = nv!!.findViewById(R.id.navMenuItemLogout)
//        navCloseBtn = nv!!.findViewById(R.id.navCloseBtn)
//    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(VistarkContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    private fun initEvents() {
        aqsIvCloseBtn.setOnClickListener {
            finish()
        }
//        aqsUserAvatar.setOnClickListener {
//            dl!!.openDrawer(GravityCompat.END, true)
//        }
//        navMenuItemAccountInfo.setOnClickListener {
//            dl!!.closeDrawer(GravityCompat.END, true)
//            val intent = Intent(this, AccountInfoActivity::class.java)
//            startActivity(intent)
//        }
//
//        navMenuItemPasswordUpdate.setOnClickListener {
//            dl!!.closeDrawer(GravityCompat.END, true)
//            val intent = Intent(this, ChangePasswordActivity::class.java)
//            startActivity(intent)
//        }
//
//        navMenuItemLogout.setOnClickListener {
//            dl!!.closeDrawer(GravityCompat.END, true)
//            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).apply {
//                titleText = "Bạn thực sự muốn đăng xuất khỏi tài khoản hiện tại?"
//                contentText = "ĐĂNG XUẤT"
//                setConfirmButton("Đồng ý") {
//                    it.dismiss()
//                    val intent = Intent(this@QrScanActivity, SignInActivity::class.java)
//                    intent.flags =
//                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    startActivity(intent)
//                    finish()
//                }
//                setCancelButton("Không") {
//                    it.dismissWithAnimation()
//                }
//                show()
//            }
//        }
//
//        navCloseBtn.setOnClickListener {
//            dl!!.closeDrawer(GravityCompat.END, true)
//        }
    }

    private fun setUpZxingStatusViewPosition() {
        zxingViewfinderView.onHaveBottomPosition = { bottomPosition ->
            zxingStatusView.y = bottomPosition + DimensionHelper.dpToPx(this, 52F)
        }
    }

    private fun initViews() {
        zxingViewfinderView = findViewById(R.id.zxing_viewfinder_view)
        zxingStatusView = findViewById(R.id.zxing_status_view)
    }

//    private fun updateToolbarPosition() {
//        listener = OnGlobalLayoutListener {
//            aqsToolbar.viewTreeObserver.removeOnGlobalLayoutListener(listener)
//            val marginLayoutParams = aqsToolbar.layoutParams as RelativeLayout.LayoutParams
//            marginLayoutParams.setMargins(
//                0,
//                DimensionUtils.statusBarHeight(this@QrScanActivity),
//                0,
//                0
//            )
//            aqsToolbar.layoutParams = marginLayoutParams
//        }
//        aqsToolbar.viewTreeObserver.addOnGlobalLayoutListener(listener)
//    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        capture.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return bcScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

//    override fun onBackPressed() {
//        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).apply {
//            titleText = "Bạn thực sự muốn đóng ứng dụng?"
//            contentText = "ĐÓNG ỨNG DỤNG"
//            setConfirmButton("Đóng") {
//                it.dismiss()
//                exitProcess(0)
//            }
//            setCancelButton("Không") {
//                it.dismissWithAnimation()
//            }
//            show()
//        }
//    }
}