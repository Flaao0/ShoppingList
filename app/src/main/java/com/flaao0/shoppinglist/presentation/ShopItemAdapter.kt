package com.flaao0.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flaao0.shoppinglist.R
import com.flaao0.shoppinglist.domain.ShopItem

class ShopItemAdapter : RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>() {

    var onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

    var list = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {

        val layout = if (viewType > 0) {
            R.layout.shop_item_enabled
        } else {
            R.layout.shop_item_disabled
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        val shopItem = list[position]
        holder.textViewDescriptor.text = shopItem.name
        holder.textViewCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].condition) IS_ENABLED else IS_DISABLED
    }



    override fun getItemCount(): Int {
        return list.size
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewDescriptor: TextView = view.findViewById(R.id.textViewDescription)
        val textViewCount: TextView = view.findViewById(R.id.textViewCount)
    }

    companion object {

        const val IS_ENABLED = 1
        const val IS_DISABLED = -1

        const val MAX_POOL_SIZE = 20

    }
}