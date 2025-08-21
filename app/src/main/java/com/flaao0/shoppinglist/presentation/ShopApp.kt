package com.flaao0.shoppinglist.presentation

import android.app.Application
import com.flaao0.shoppinglist.di.DaggerApplicationComponent

class ShopApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}