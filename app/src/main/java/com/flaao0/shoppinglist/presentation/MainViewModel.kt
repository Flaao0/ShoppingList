package com.flaao0.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.flaao0.shoppinglist.data.ShopListRepositoryImpl
import com.flaao0.shoppinglist.domain.DeleteShopItemUseCase
import com.flaao0.shoppinglist.domain.EditShopItemUseCase
import com.flaao0.shoppinglist.domain.GetShopItemListUseCase
import com.flaao0.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeConditionShopItem(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(condition = !shopItem.condition)
        editShopItemUseCase.editShopItem(newShopItem)
    }


}