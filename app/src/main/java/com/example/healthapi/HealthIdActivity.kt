package com.example.healthapi

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapi.model.Healthid
import kotlinx.android.synthetic.main.activity_health_id.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthIdActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_id)

        val transactionId = intent.getStringExtra("transactionId")
        val mobileNo = intent.getStringExtra("CellNo")

        tvMobileno.text = mobileNo
        tvTransactionId.text = transactionId


        btnCreateHealthId.setOnClickListener {
            val userName = etUsername.text.toString()
            val password = etPassword.text.toString()
            val firstName = etFirstName.text.toString()
            val middleName = etMiddleName.text.toString()
            val lastName = etLastName.text.toString()
            val gender = etGender.text.toString()
            val address = etAddress.text.toString()
            val email = etemail.text.toString()

            if (userName.isEmpty()) {
                tilUsername.error = "Username cannot empty"

            }
            if (password.isEmpty()) {
                tilPassword.error = "Password cannot empty"

            }

            if (firstName.isEmpty()) {
                tilFirstName.error = "Firstname cannot empty"

            }
            if (lastName.isEmpty()) {
                tilLastName.error = "Lastname cannot empty"

            }


            if (gender.isEmpty()) {
                tilGender.error = "Gender cannot empty"

            }
            if (email.isEmpty()){
                tilEmail.error = "Email cannot empty"
            }



            fun isDataValid(): Boolean {
                var isValid = true
                if (userName.isEmpty()) {
                    tilUsername.error = "Username cannot empty"
                    isValid = false
                }
                if (userName.length <= 3 || userName.length >= 33) {
                    tilUsername.error = "Username is not valid"
                    isValid = false
                }
                if (!isEmailAddressValid(email)) {
                    isValid = false
                }
                if (gender == "male" || gender == "female" || gender == "transgender" || gender == "m" || gender == "f" || gender == "tg" || gender == "M" || gender == "F" || gender == "TG" || gender == "Male" || gender == "Female" || gender == "Transgender") {

                } else {
                    tilGender.error = "Enter Valid Gender"
                    isValid = false
                }

                return isValid
            }




            if (firstName.isNotEmpty() && lastName.isNotEmpty() && gender.isNotEmpty() && isDataValid() && password.isNotEmpty()) {
                if (transactionId != null) {
                    createHealthId(
                        userName,
                        firstName,
                        middleName,
                        lastName,
                        gender,
                        address,
                        email,
                        password,
                        transactionId,
                        mobileNo
                    )
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    fun isEmailAddressValid(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        }
        tilEmail.error = "Invalid Email Address"
        return false
    }


    private fun createHealthId(
        userName: String,
        firstName: String,
        middleName: String,
        lastName: String,
        gender: String,
        address: String,
        email: String,
        password: String,
        transactionId: String,
        mobileNo: String?
    ) {
        val apiClient = Network.getInstance()
        val healthid = Healthid(
            transactionId,
            email,
            firstName,
            lastName,
            middleName,
            mobileNo.toString(),
            password,
            transactionId,
            userName
        )
        val call = apiClient.createHealthId(healthid)
        call.enqueue(object : Callback<Healthid> {
            override fun onResponse(call: Call<Healthid>, response: Response<Healthid>) {
                if (response.code() == 201) {
                    Toast.makeText(
                        this@HealthIdActivity,
                        "Health Id created Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@HealthIdActivity, QrActivity::class.java)
                    intent.putExtra("gender", gender)
                    intent.putExtra("firstName", firstName)
                    intent.putExtra("middleName", middleName)
                    intent.putExtra("lastName", lastName)
                    intent.putExtra("mobileNo", mobileNo)
                    intent.putExtra("id", transactionId)
                    startActivity(intent)
                }
                if (response.code() == 500) {
                    Toast.makeText(
                        this@HealthIdActivity,
                        "Health Id already Available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Healthid>, t: Throwable) {
                Toast.makeText(this@HealthIdActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}
