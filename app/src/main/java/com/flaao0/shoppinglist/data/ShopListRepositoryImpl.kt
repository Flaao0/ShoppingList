package com.flaao0.shoppinglist.data

import com.flaao0.shoppinglist.domain.ShopItem
import com.flaao0.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoGenerateId = 0

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoGenerateId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopItemById(id: Int): ShopItem {
         val shopItem = shopList.find {
            it.id == id
        } ?: throw NullPointerException("Element id = null")
        return shopItem
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        shopList.remove(oldShopItem)
        shopList.add(shopItem)
    }
}