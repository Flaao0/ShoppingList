package com.flaao0.shoppinglist.domain

class GetShopItemByIdUseCase(private val shopItemRepository: ShopListRepository) {

    fun getShopItemById(id: Int): ShopItem {
        return shopItemRepository.getShopItemById(id)
    }
}