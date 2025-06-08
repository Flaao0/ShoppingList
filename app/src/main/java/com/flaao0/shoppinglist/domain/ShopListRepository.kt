package com.flaao0.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun getShopItemById(id: Int): ShopItem

    fun editShopItem(shopItem: ShopItem)
}