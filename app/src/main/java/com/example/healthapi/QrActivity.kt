package com.example.healthapi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
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

        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(firstName, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            ivQr.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

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