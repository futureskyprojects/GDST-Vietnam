package vn.vistark.qrinfoscanner.ui.technical_data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_technical_data.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.android.synthetic.main.component_top_search_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.core.extensions.keyboard.HideKeyboardExtension.Companion.HideKeyboard
import vn.vistark.qrinfoscanner.core.helpers.VistarkContextWrapper
import vn.vistark.qrinfoscanner.core.overrides.VistarkActivity
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataDTO
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataUpdateDTO
import vn.vistark.qrinfoscanner.domain.api.requests.technical_data.GetTechnicalDataBody
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.entities.GDSTInfomationFishUp
import vn.vistark.qrinfoscanner.domain.entities.GDSTMaterialShip
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData
import vn.vistark.qrinfoscanner.domain.mock_entities.TechnicalData
import vn.vistark.qrinfoscanner.helpers.FloatAddButtonHelper
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTTopSearchBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert
import vn.vistark.qrinfoscanner.helpers.alert_helper.technical_data.TechnicalDataUpdateDialog.Companion.showUpdateTechnicalDataAlert
import vn.vistark.qrinfoscanner.ui.traceable_object_information.TraceableObjectInformationActivity

class TechnicalDataActivity : VistarkActivity() {
    private var materialShipId: Int = -1

    private val requestSpecialCode = 11122

    private val technicalDatas = ArrayList<GDSTTechnicalData>()
    private lateinit var adapter: TechnicalDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technical_data)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        syncTechnicalData()

        initDataEvents()

        masterLayout.setOnClickListener { HideKeyboard() }
        atdRvVessels.setOnClickListener { HideKeyboard() }
    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        materialShipId = intent.getIntExtra(GDSTMaterialShip::class.java.simpleName, -1)
        if (materialShipId <= 0) {
            Toast.makeText(this, getString(R.string.ktxdtnldc), Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }
        atdTvLabel.text =
            getString(R.string.ttkt) + " [#${
                materialShipId.toString().padStart(Config.padSize, '0')
            }]"
    }

    private fun initEvents() {
        atdBackButton.clickAnimate {
            finish()
        }
        FloatAddButtonHelper.initialize(cfabIvIcon, cfabLnAddBtn) {
            showUpdateTechnicalDataAlert({ techData ->
                if (techData != null) {
                    techData.materialShipId = materialShipId

                    val loading = this.showLoadingAlert()
                    loading.show()
                    GlobalScope.launch {
                        try {
                            val res =
                                ApiService.mAPIServices.postGDSTTechnicalData(techData).await()
                                    ?: throw  Exception("ko phan giai dc")
                            runOnUiThread { loading.cancel() }
                            runOnUiThread(this@TechnicalDataActivity::syncTechnicalData)

                        } catch (e: Exception) {
                            runOnUiThread { loading.cancel() }
                            e.printStackTrace()
                            runOnUiThread {
                                showAlertConfirm(getString(R.string.tttktktc))
                            }
                        }
                    }
                }
            }, lastedEventIdInMaterialShip = technicalDatas.lastOrNull()?.eventId ?: 0)
        }
    }

    private fun syncTechnicalData() {
        technicalDatas.clear()
        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response = ApiService.mAPIServices.getGDSTTechnicalData(
                    GetTechnicalDataBody(materialShipId)
                ).await()
                runOnUiThread { loading.cancel() }
                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                runOnUiThread {

                    technicalDatas.addAll(response)
                    adapter.notifyDataSetChanged()

                    if (technicalDatas.isEmpty()) {
                        cfabIvIcon.performClick()
                    }
                    aaEdtSearchBar.initGDSTTopSearchBar(technicalDatas) {
                        technicalDatas.clear()
                        technicalDatas.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm(getString(R.string.kldtdlcs))
                }
                e.printStackTrace()
            } finally {

            }
        }
    }

    fun add(s: GDSTTechnicalData) {
        technicalDatas.add(0, s)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        atdRvVessels.setHasFixedSize(true)
        atdRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = TechnicalDataAdapter(technicalDatas)
        atdRvVessels.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onClick = { start(it) }
        adapter.onEdit = {
            showUpdateTechnicalDataAlert({ techData ->
                if (techData != null) {
                    val resData = GDSTTechnicalData.From(techData)
                    resData.id = it.id

                    val loading = this.showLoadingAlert()
                    loading.show()

                    GlobalScope.launch {
                        try {
                            val res =
                                ApiService.mAPIServices.postGDSTTechnicalDataUpdate(
                                    GDSTTechnicalDataUpdateDTO.mapFrom(resData)
                                ).await()
                                    ?: throw  Exception("ko phan giai dc")
                            runOnUiThread { loading.cancel() }
                            runOnUiThread(this@TechnicalDataActivity::syncTechnicalData)
                        } catch (e: Exception) {
                            runOnUiThread { loading.cancel() }
                            e.printStackTrace()
                            runOnUiThread {
                                showAlertConfirm(getString(R.string.cnttktktc))
                            }
                        }
                    }
                }
            }, GDSTTechnicalDataDTO.From(it), it.eventId.toString())
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(VistarkContextWrapper.wrap(newBase, Config.LanguageCode))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    fun start(technicalData: GDSTTechnicalData) {
        val intent = Intent(this, TraceableObjectInformationActivity::class.java)
        intent.putExtra(TechnicalData::class.java.simpleName, technicalData.id)
        intent.putExtra(
            GDSTInfomationFishUp::class.java.simpleName,
            technicalData.informationFishingUp
        )
        startActivityForResult(intent, requestSpecialCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestSpecialCode && resultCode == RESULT_OK) {
            syncTechnicalData()
        }
    }
}