package com.example.healthapi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_qr.*

class QrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        val intent = intent
        val gender = intent.getStringExtra("gender")
        val firstName = intent.getStringExtra("firstName")
        val middleName = intent.getStringExtra("middleName")
        val lastName = intent.getStringExtra("lastName")
        val mobileNo = intent.getStringExtra("mobileNo")
        val id = intent.getStringExtra("transactionId")

        tvAge.text = gender
        tvName.text = "$firstName $lastName"
        tvMobile.text = "$mobileNo-eIe123"
        tvHealthId.text = "$firstName.$lastName@ndhm"
           setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        btnView.setOnClickListener {
            val intent = Intent(this, HealthIdData::class.java)
            startActivity(intent)


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}