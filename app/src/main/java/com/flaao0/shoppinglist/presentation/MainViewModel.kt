package com.flaao0.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flaao0.shoppinglist.domain.DeleteShopItemUseCase
import com.flaao0.shoppinglist.domain.EditShopItemUseCase
import com.flaao0.shoppinglist.domain.GetShopItemListUseCase
import com.flaao0.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import jakarta.inject.Inject // Изменено с javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopItemListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
) : ViewModel() {

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