package com.flaao0.shoppinglist.domain

interface ShopListRepository {

    fun getShopList(): List<ShopItem>

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun getShopItemById(id: Int): ShopItem

    fun editShopItem(shopItem: ShopItem)
}