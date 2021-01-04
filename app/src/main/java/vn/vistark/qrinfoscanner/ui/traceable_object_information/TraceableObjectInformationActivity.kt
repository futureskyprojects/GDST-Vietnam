package vn.vistark.qrinfoscanner.ui.traceable_object_information

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_traceable_object_information.*
import kotlinx.android.synthetic.main.component_float_add_btn.*
import kotlinx.android.synthetic.main.component_top_search_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.domain.constants.Config
import vn.vistark.qrinfoscanner.domain.mock_entities.TechnicalData
import vn.vistark.qrinfoscanner.core.extensions.ViewExtension.Companion.clickAnimate
import vn.vistark.qrinfoscanner.domain.DTOs.GDSTTechnicalDataUpdateDTO
import vn.vistark.qrinfoscanner.domain.api.requests.technical_data.GetTechnicalDataBody
import vn.vistark.qrinfoscanner.domain.api.requests.technical_data.GetTechnicalDataDetailBody
import vn.vistark.qrinfoscanner.domain.constants.Config.Companion.showLog
import vn.vistark.qrinfoscanner.domain.constants.GDSTStorage
import vn.vistark.qrinfoscanner.domain.entities.GDSTInfomationFishUp
import vn.vistark.qrinfoscanner.domain.entities.GDSTTechnicalData
import vn.vistark.qrinfoscanner.helpers.TopSearchBarHelper.Companion.initGDSTTopSearchBar
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert

class TraceableObjectInformationActivity : AppCompatActivity() {
    private var specials: ArrayList<GDSTInfomationFishUp> = ArrayList()

    private var technicalData: GDSTTechnicalData? = null

    private lateinit var adapter: TOIAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traceable_object_information)

        initTranshipmentData()

        initEvents()

        initRecyclerView()

        initDataEvents()

        setResult(RESULT_CANCELED)

    }

    @SuppressLint("SetTextI18n")
    private fun initTranshipmentData() {
        val specialData =
            intent.getStringExtra(GDSTInfomationFishUp::class.java.simpleName) ?: ""

        val technicalDataId =
            intent.getIntExtra(TechnicalData::class.java.simpleName, -1)

        if (specialData.isEmpty() || technicalDataId <= 0) {
            Toast.makeText(
                this,
                "Không thể xác định thông tin kỹ thuật của mẻ lưới được chọn",
                Toast.LENGTH_SHORT
            )
                .show()
            finish()
            return
        }

        try {
            val tempSpecials = ArrayList(
                Gson().fromJson(
                    specialData,
                    Array<GDSTInfomationFishUp>::class.java
                ).toList()
            )
            specials.addAll(tempSpecials)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        checkAndInitMissData()

        atoiTvLabel.text =
            "Thông tin sản lượng [#${technicalDataId.toString().padStart(Config.padSize, '0')}]"

        loadTechnicalData(technicalDataId)
    }

    private fun loadTechnicalData(id: Int) {
        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response = ApiService.mAPIServices.getGDSTTechnicalDataDetail(
                    GetTechnicalDataDetailBody(id)
                ).await()
                runOnUiThread { loading.cancel() }
                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                runOnUiThread {
                    technicalData =
                        response.firstOrNull() ?: throw Exception("Không phân dải được KQ trả về")
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm("Không lấy được tập dữ liệu có sẵn")
                }
                e.printStackTrace()
            } finally {

            }
        }
    }

    private fun checkAndInitMissData() {
        GDSTStorage.GDSTSpecies?.forEach {
            val f = GDSTInfomationFishUp(0F, it.id, "KG")
            if (!specials.any { x -> f.spiceId == x.spiceId }) {
                specials.add(f)
            }
        }
    }

    private fun initEvents() {
        atoiBackButton.clickAnimate {
            if (technicalData == null)
                finish()
            val loading = this.showLoadingAlert()
            loading.show()
            GlobalScope.launch {
                try {
                    val response =
                        ApiService.mAPIServices.postGDSTTechnicalDataUpdate(
                            GDSTTechnicalDataUpdateDTO.mapFrom(technicalData!!)
                        ).await()
                    runOnUiThread { loading.cancel() }
                    if (response == null)
                        throw Exception("Không phân dải được KQ trả về")



                    runOnUiThread {
                        showAlertConfirm("Đã lưu dữ liệu thành công " + Gson().toJson(response), {
                            setResult(RESULT_OK)
                            finish()
                        })
                    }
                } catch (e: Exception) {
                    runOnUiThread { loading.cancel() }
                    runOnUiThread {
                        showAlertConfirm("Không thể cập nhật được dữ liệu")
                    }
                    e.printStackTrace()
                } finally {

                }
            }
        }
    }

    private fun initRecyclerView() {
        atoiRvVessels.setHasFixedSize(true)
        atoiRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = TOIAdapter(specials)
        atoiRvVessels.adapter = adapter
    }

    private fun initDataEvents() {
        adapter.onWeightChange = {
//            val index = specials.indexOfFirst { x -> x.spiceId == it.spiceId }
//            specials[index].quantification = it.quantification
//            adapter.notifyDataSetChanged()
            technicalData?.informationFishingUp = Gson().toJson(specials)
            technicalData?.eventQuantification = specials.map { x -> x.quantification }.sum()
            showLog(Gson().toJson(specials))
        }
    }

}