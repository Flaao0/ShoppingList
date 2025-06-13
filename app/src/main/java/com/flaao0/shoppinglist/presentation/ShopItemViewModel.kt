package com.flaao0.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flaao0.shoppinglist.data.ShopListRepositoryImpl
import com.flaao0.shoppinglist.domain.AddShopItemUseCase
import com.flaao0.shoppinglist.domain.EditShopItemUseCase
import com.flaao0.shoppinglist.domain.GetShopItemByIdUseCase
import com.flaao0.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemLD

    private val _isClosing = MutableLiveData<Unit>()
    val isClosing: LiveData<Unit>
        get() = _isClosing

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemByIdUseCase.getShopItemById(shopItemId)
        _shopItemLD.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseInt(inputCount)
        if (checkCorrect(name, count)) {
            val shopItem = ShopItem(name = name, count = count, true)
            addShopItemUseCase.addShopItem(shopItem)
            shouldCloseScreen()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseInt(inputCount)
        if (checkCorrect(name, count)) {
            _shopItemLD.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                shouldCloseScreen()
            }
        }
    }

    private fun checkCorrect(name: String, count: Int): Boolean {
        var flag = true
        if (name.isBlank()) {
            _errorInputName.value = true
            flag = false
        }
        if (count < 1) {
            _errorInputCount.value = true
            flag = false
        } else {
            flag = true
        }
        return flag
    }

    private fun shouldCloseScreen() {
        _isClosing.value = Unit
    }

     fun resetErrorInputName() {
        _errorInputName.value = false
    }

     fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseInt(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

}