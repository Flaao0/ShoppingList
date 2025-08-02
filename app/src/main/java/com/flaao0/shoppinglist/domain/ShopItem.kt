package com.flaao0.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val condition: Boolean,
    var id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = 0
    }
}
