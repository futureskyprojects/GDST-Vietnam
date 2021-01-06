package vn.vistark.qrinfoscanner.ui.shipment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_shipments.*
import kotlinx.android.synthetic.main.activity_shipments.btmNavLayout
import kotlinx.android.synthetic.main.activity_shipments.masterLayout
import kotlinx.android.synthetic.main.component_bottom_nav.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.android.synthetic.main.component_top_search_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.DatetimeHelper.Companion.Format
import vn.vistark.qrinfoscanner.core.helpers.MyContextWrapper
import vn.vistark.qrinfoscanner.core.helpers.QRGenerator.Companion.QRBitmap
import vn.vistark.qrinfoscanner.core.helpers.ResourceSaver.Companion.SaveBitmap
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTShipmentCreateDTO
import vn.vistark.qrinfoscanner.domain.api.IApiService
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.constants.Config.Companion.padSize
import vn.vistark.qrinfoscanner.domain.entities.GDSTShipment
import vn.vistark.qrinfoscanner.domain.mock_entities.Shipment
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTBottomBar
import vn.vistark.qrinfoscanner.helpers.BottomNavigationBarHelper.Companion.initGDSTSmartBottomBar
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTSmartTopSearchBar
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTTopSearchBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.ui.material_batch.MaterialBatchActivity
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class ShipmentsActivity : AppCompatActivity() {
    private val shipments = ArrayList<GDSTShipment>()
    private lateinit var adapter: ShipmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipments)

        initGDSTBottomBar(cbnBnvBottomNav, cbnBtnCenter, -1)
        btmNavLayout.initGDSTSmartBottomBar(asRvShipments)
        topSearchBar.initGDSTSmartTopSearchBar(asRvShipments)

        initEvents()

        initRecyclerView()

        syncShipments()

        initDataEvents()

        masterLayout.setOnClickListener { HideKeyboard() }
        rlOut.setOnClickListener { HideKeyboard() }

    }

    private fun initDataEvents() {
        adapter.onEdit = {
            showAlertConfirm(
                "Bạn có chắc muốn xóa dữ liệu lô hàng [#${
                    it.id.toString()
                        .padStart(padSize, '0')
                }] hay không?",
                {
                    Toast.makeText(
                        this,
                        "Chức năng xóa hiện không được cho phép",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        adapter.onClick = {
            gotoRawBatch(it.id)
        }
    }

    private fun gotoRawBatch(shipmentId: Int) {
        val intent = Intent(this, MaterialBatchActivity::class.java)
        intent.putExtra(Shipment::class.java.simpleName, shipmentId)
        startActivity(intent)
    }

    private fun removeShipmentView(shipment: Shipment) {
        val index = shipments.indexOfFirst { it.id == shipment.Id }
        shipments.removeAt(index)
        adapter.notifyDataSetChanged()
    }

    private fun initEvents() {
        asBackButton.clickAnimate {
            finish()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            val shipment = GDSTShipment((shipments.lastOrNull()?.id ?: 0) + 1)
            this.showAlertConfirm(
                getString(R.string.bctsmtlhms) + " #${
                    shipment.id.toString().padStart(
                        padSize,
                        '0'
                    )
                } " + getString(R.string.vao_luc) + " [${Date().Format("HH:mm dd-MM-yyyy")}]",
                {
                    // Tạo bitmap
                    val path =
                        "${IApiService.BASE_URL}${Config.qrPath}${shipment.id}"
                    val bmp = QRBitmap(path)
                    if (bmp == null) {
                        showAlertConfirm(getString(R.string.tlhktc))
                        return@showAlertConfirm
                    }

                    SaveBitmap("/shipment_qr/qr_${shipment.id}.png", bmp) { path ->
                        runOnUiThread {
                            // Upload lên server
                            val file = File(path)
                            val requestFile: RequestBody =
                                RequestBody.create(MediaType.parse("multipart/form-data"), file)
                            val body =
                                MultipartBody.Part.createFormData("image", file.name, requestFile)

                            val loading = this.showLoadingAlert()
                            loading.show()
                            GlobalScope.launch {
                                try {
                                    // Tải QR lên máy chủ
                                    val uploadedQrCodePath =
                                        ApiService.mAPIServices.postGDSTUploadQrCode(body).await()
                                            ?: throw Exception("Ko nhan dc du lieu")

                                    // Tạo mới lô nglieu
                                    val dto =
                                        GDSTShipmentCreateDTO(uploadedQrCodePath.result.path)

                                    // Cập nhật lên server
                                    val response =
                                        ApiService.mAPIServices.postGDSTShipment(dto).await()
                                            ?: throw Exception("Không gửi dữ liệu lô nglieu len dc")

                                    runOnUiThread { loading.cancel() }
                                    runOnUiThread {
                                        syncShipments()
                                        gotoRawBatch(response.idShipment)
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    runOnUiThread { loading.cancel() }
                                    runOnUiThread {
                                        showAlertConfirm(getString(R.string.tlhktc_2))
                                    }
                                }
                            }
                        }
                    }
                })
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    private fun syncShipments() {
        shipments.clear()
        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response = ApiService.mAPIServices.getGDSTShipment().await()
                runOnUiThread { loading.cancel() }
                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                runOnUiThread {
//                    updateCount(response.size)
                    response.forEach { ship ->
                        add(ship)
                    }

                    aaEdtSearchBar.initGDSTTopSearchBar(shipments) {
                        shipments.clear()
                        shipments.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm(getString(R.string.kldtdlcs))
                }
                e.printStackTrace()
            }
        }
    }

    fun add(s: GDSTShipment) {
        shipments.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        asRvShipments.setHasFixedSize(true)
        asRvShipments.layoutManager = LinearLayoutManager(this)

        adapter = ShipmentAdapter(shipments)
        asRvShipments.adapter = adapter
    }
}