package com.flaao0.shoppinglist.domain

import javax.inject.Inject

class GetShopItemByIdUseCase @Inject constructor (private val shopItemRepository: ShopListRepository) {

    suspend fun getShopItemById(id: Int): ShopItem {
        return shopItemRepository.getShopItemById(id)
    }
}