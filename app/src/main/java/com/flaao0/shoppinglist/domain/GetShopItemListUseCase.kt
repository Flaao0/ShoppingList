package com.flaao0.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShopItemListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }
}