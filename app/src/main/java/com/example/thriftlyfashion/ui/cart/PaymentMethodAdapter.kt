package com.example.thriftlyfashion.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.thriftlyfashion.PaymentMethod
import com.example.thriftlyfashion.R

class PaymentMethodAdapter(
    private val paymentMethods: List<PaymentMethod>,
    private val onClick: (PaymentMethod, Int) -> Unit
) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    private var sPosition: Int = RecyclerView.NO_POSITION

    inner class PaymentMethodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.nama)
        val paymentCard: LinearLayout = view.findViewById(R.id.paymentCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment_method, parent, false)
        return PaymentMethodViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        holder.logo.setImageResource(paymentMethod.logoResId)
        holder.name.text = paymentMethod.name

        if (holder.adapterPosition == sPosition) {
            holder.paymentCard.setBackgroundResource(R.drawable.border_selected)
        } else {
            holder.paymentCard.setBackgroundResource(R.drawable.border_default)
        }

        holder.paymentCard.setOnClickListener {
            val selectedPosition = holder.adapterPosition
            if (selectedPosition != RecyclerView.NO_POSITION) {
                sPosition = selectedPosition
                notifyDataSetChanged()
                onClick(paymentMethod, selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int = paymentMethods.size
}
