package com.flaao0.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flaao0.shoppinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textViewDescriptor: TextView = view.findViewById(R.id.textViewDescription)
    val textViewCount: TextView = view.findViewById(R.id.textViewCount)
}