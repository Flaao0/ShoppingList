package com.flaao0.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flaao0.shoppinglist.domain.ShopItem
import com.flaao0.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private var autoGenerateId = 0

    init {
        for (i in 0 until 5) {
            val shopItem = ShopItem("Name$i", i, true)
            addShopItem(shopItem)
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoGenerateId++
        }
        shopList.add(shopItem)
        shopListLD.value = shopList.toList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        shopListLD.value = shopList.toList()
    }

    override fun getShopItemById(id: Int): ShopItem {
         val shopItem = shopList.find {
            it.id == id
        } ?: throw NullPointerException("Element id = null1")
        return shopItem
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        shopList.remove(oldShopItem)
        shopList.add(shopItem)
        shopListLD.value = shopList.toList()
    }
}