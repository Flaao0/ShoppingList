package com.flaao0.shoppinglist.di

import android.app.Application
import android.content.Context
import com.flaao0.shoppinglist.data.ShopListProvider
import com.flaao0.shoppinglist.presentation.MainActivity
import com.flaao0.shoppinglist.presentation.ShopItemActivity
import com.flaao0.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [DataModel::class, ViewModelModule::class]
)
interface ApplicationComponent {


    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    fun inject(provider: ShopListProvider)


    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
