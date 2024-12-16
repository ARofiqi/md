package com.example.thriftlyfashion.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thriftlyfashion.PaymentMethod
import com.example.thriftlyfashion.R

class PaymentMethodActivity : AppCompatActivity() {
    private var selectedPosition: Int = -1
    private lateinit var paymentMethods: List<PaymentMethod>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        val btnBack: ImageView = findViewById(R.id.id_btnBack)
        btnBack.setOnClickListener { finish() }

        paymentMethods = listOf(
            PaymentMethod(R.drawable.logo_ovo, "OVO"),
            PaymentMethod(R.drawable.logo_linkaja, "LinkAja!"),
            PaymentMethod(R.drawable.logo_mastercard, "Mastercard**46")
        )

        setupRecyclerView(
            recyclerView = findViewById(R.id.isConnected),
            paymentMethods = paymentMethods
        )

        val btnPilih: CardView = findViewById(R.id.btn_pilih)
        btnPilih.setOnClickListener {
            if (selectedPosition != -1) {
                val selectedPaymentMethod = paymentMethods[selectedPosition]

                val bundle = Bundle().apply {
                    putString("PAYMENT_METHOD_NAME", selectedPaymentMethod.name)
                    putInt("PAYMENT_METHOD_LOGO", selectedPaymentMethod.logoResId)
                }

                val cartFragment = CartFragment().apply {
                    arguments = bundle
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, cartFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, paymentMethods: List<PaymentMethod>) {
        val adapter = PaymentMethodAdapter(
            paymentMethods,
            onClick = { paymentMethod, position ->
                selectedPosition = position
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
