package com.flaao0.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.flaao0.shoppinglist.R
import com.flaao0.shoppinglist.domain.ShopItem

class ShopItemAdapter: ListAdapter<ShopItem, ShopItemViewHolder> (
    ShopItemDiffCallback()
) {

    var onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {

        val layout = if (viewType == 1) {
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
        val shopItem = getItem(position)
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
        return if (getItem(position).condition) IS_ENABLED else IS_DISABLED
    }

    companion object {
        const val IS_ENABLED = 1
        const val IS_DISABLED = -1
        const val MAX_POOL_SIZE = 20
    }
}