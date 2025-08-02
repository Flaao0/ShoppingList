package com.flaao0.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flaao0.shoppinglist.data.ShopListRepositoryImpl
import com.flaao0.shoppinglist.domain.AddShopItemUseCase
import com.flaao0.shoppinglist.domain.EditShopItemUseCase
import com.flaao0.shoppinglist.domain.GetShopItemByIdUseCase
import com.flaao0.shoppinglist.domain.ShopItem

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)

    private val _isClosing = MutableLiveData<Unit>()
    val isClosing: LiveData<Unit>
        get() = _isClosing

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemLD

    fun addShopItem(inputName: String, inputCount: String) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validation(name, count)) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            closeScreen()
        }
    }

    fun getShopItemById(id: Int) {
         getShopItemByIdUseCase.getShopItemById(id).also {
            _shopItemLD.value = it
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validation(name, count)) {
            _shopItemLD.value?.let {
                val shopItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(shopItem)
                closeScreen()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validation(inputName: String, inputCount: Int): Boolean {
        var flag = true
        if (inputName.isEmpty()) {
            flag = false
            _errorInputName.value = true
        }
        if (inputCount <= 0) {
            flag = false
            _errorInputCount.value = true
        }
        return flag
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun closeScreen() {
        _isClosing.value = Unit
    }
}