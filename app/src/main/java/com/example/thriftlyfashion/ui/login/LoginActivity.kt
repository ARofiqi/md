package com.example.thriftlyfashion.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thriftlyfashion.R
import com.example.thriftlyfashion.remote.SharedPrefManager
import com.example.thriftlyfashion.remote.api.ApiService
import com.example.thriftlyfashion.remote.api.RetrofitClient
import com.example.thriftlyfashion.remote.model.LoginRequest
import com.example.thriftlyfashion.ui.MainActivity
import com.example.thriftlyfashion.ui.signup.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var ivTogglePassword: ImageView
    private lateinit var tvForgetPassword: TextView
    private lateinit var tvRegister: TextView

    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        ivTogglePassword = findViewById(R.id.ivTogglePassword)
        tvForgetPassword = findViewById(R.id.tvForgetPassword)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener { handleLogin() }
        ivTogglePassword.setOnClickListener { togglePasswordVisibility() }

        tvForgetPassword.setOnClickListener {
            showCustomToast("Lupa password? Fitur ini belum tersedia", R.drawable.logo)
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showCustomToast("Email dan password tidak boleh kosong", R.drawable.logo)
            return
        }

        val loginRequest = LoginRequest(email, password)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val apiService = RetrofitClient.createService(ApiService::class.java)
                    apiService.login(loginRequest)
                }

                if (response.isSuccessful && response.body()?.message == "Login successful") {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    val user = loginResponse?.user
                    val sharedPrefManager = SharedPrefManager(this@LoginActivity)

                    token?.let { sharedPrefManager.saveToken(it) }
                    val phoneNumber = user?.phoneNumber ?: ""

                    sharedPrefManager.saveUserId(user?.userId ?: 0)
                    sharedPrefManager.saveUserName(user?.name ?: "")
                    sharedPrefManager.saveUserEmail(user?.email ?: "")
                    sharedPrefManager.saveUserPhoneNumber(phoneNumber)

                    showCustomToast("Login Berhasil", R.drawable.logo)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showCustomToast("Gagal Melakukan Login", R.drawable.logo)
                }
            } catch (e: Exception) {
                showCustomToast("Gagal Melakukan Login", R.drawable.logo)
            }
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            ivTogglePassword.setImageResource(R.drawable.baseline_visibility_24)
        } else {
            etPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT
            ivTogglePassword.setImageResource(R.drawable.baseline_visibility_off_24)
        }
        isPasswordVisible = !isPasswordVisible
        etPassword.setSelection(etPassword.text.length)
    }

    private fun showCustomToast(message: String, iconResId: Int) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

        val toastIcon = layout.findViewById<ImageView>(R.id.toast_icon)
        val toastMessage = layout.findViewById<TextView>(R.id.toast_message)

        toastIcon.setImageResource(iconResId)
        toastMessage.text = message

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}