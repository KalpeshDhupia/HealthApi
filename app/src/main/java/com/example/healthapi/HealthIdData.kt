package com.example.healthapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthapi.model.Healthid
import kotlinx.android.synthetic.main.activity_health_id_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthIdData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_id_data)
        callApi()
    }

    private fun callApi() {
        val apiClient = Network.getInstance()
        val call = apiClient.getHealthIdData()
        call.enqueue(object : Callback<List<Healthid>> {
            override fun onResponse(
                call: Call<List<Healthid>>,
                response: Response<List<Healthid>>
            ) {
                if (response.body() != null) {
                    response.body()?.let {
                        buildData(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Healthid>>, t: Throwable) {
                Toast.makeText(this@HealthIdData, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun buildData(list: List<Healthid>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val postAdapter = HealthAdapter(list)
        recyclerView.adapter = postAdapter
    }
}