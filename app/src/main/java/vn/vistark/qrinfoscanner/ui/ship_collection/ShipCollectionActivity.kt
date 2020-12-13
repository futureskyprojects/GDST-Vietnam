package vn.vistark.qrinfoscanner.ui.ship_collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ship_collection.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.vistark.qrinfoscanner.R
import vn.vistark.qrinfoscanner.core.api.ApiService
import vn.vistark.qrinfoscanner.core.extensions.Retrofit2Extension.Companion.await
import vn.vistark.qrinfoscanner.domain.entities.GDSTShip
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showAlertConfirm
import vn.vistark.qrinfoscanner.helpers.alert_helper.AlertHelper.Companion.showLoadingAlert

class ShipCollectionActivity : AppCompatActivity() {

    val ships: ArrayList<GDSTShip> = ArrayList()
    lateinit var adapter: ShipCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ship_collection)

        updateCount(0)

        initRecyclerView()

        syncShips()
    }

    fun updateCount(count: Int = 0) {
        ascTvName.text = "Dữ liệu tàu ($count)"
    }

    private fun syncShips() {
        val loading = this.showLoadingAlert()
        loading.show()
        GlobalScope.launch {
            try {
                val response = ApiService.mAPIServices.getGDSTShip().await()
                runOnUiThread { loading.cancel() }
                if (response == null)
                    throw Exception("Không phân dải được KQ trả về")

                runOnUiThread {
                    updateCount(response.size)
                    response.forEach { ship ->
                        ships.add(ship)
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread { loading.cancel() }
                runOnUiThread {
                    showAlertConfirm("Không lấy được tập dữ liệu tàu có sẵn")
                }
                e.printStackTrace()
            }
        }
    }

    private fun initRecyclerView() {
        ascRvVessels.setHasFixedSize(true)
        ascRvVessels.layoutManager = LinearLayoutManager(this)

        adapter = ShipCollectionAdapter(ships)

        ascRvVessels.adapter = adapter
    }
}