package com.flaao0.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flaao0.shoppinglist.data.ShopListRepositoryImpl
import com.flaao0.shoppinglist.domain.DeleteShopItemUseCase
import com.flaao0.shoppinglist.domain.EditShopItemUseCase
import com.flaao0.shoppinglist.domain.GetShopItemListUseCase
import com.flaao0.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeConditionShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(condition = !shopItem.condition)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }
}