package com.example.healthapi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapi.model.Mobileno
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSubmit.setOnClickListener {
            if (editTextPhone.text != null) {
                if (editTextPhone.text!!.length == 10) {
                    addMobileNo(editTextPhone.text.toString())
                } else {
                    tilEditTextPhone.setError("Enter Valid Number")
                }
            } else {
                Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addMobileNo(no: String) {
        val apiClient = Network.getInstance()
        val mobileno = Mobileno(no.toLong(), no)
        val call = apiClient.addMobileNo(mobileno)
        call.enqueue(object : Callback<Mobileno> {
            override fun onResponse(call: Call<Mobileno>, response: Response<Mobileno>) {
                if (response.code() == 201) {
                    Toast.makeText(
                        this@MainActivity,
                        "Otp verification successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    var addedNo = response.body()
                    val intent = Intent(this@MainActivity, HealthIdActivity::class.java)
                    var txnId = addedNo?.id.toString() + "a2w-3we-3"
                    intent.putExtra("transactionId", txnId)
                    intent.putExtra("CellNo", addedNo?.mobile)
                    startActivity(intent)
                }
                if (response.code() == 500) {
                    Toast.makeText(this@MainActivity, "Number already exists", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Mobileno>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}