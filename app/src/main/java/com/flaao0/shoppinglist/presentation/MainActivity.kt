package com.flaao0.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.flaao0.shoppinglist.R
import com.flaao0.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopItem: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        llShopItem = findViewById(R.id.llShopList)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            showItems(it)
            Log.d("MainActivity1", it.toString())
        }


    }

    private fun showItems(list: List<ShopItem>) {
        llShopItem.removeAllViews()
        for (shopItem in list) {
            val inflateId = if (shopItem.condition) {
                R.layout.shop_item_enabled
            } else {
                R.layout.shop_item_disabled
            }
            val view = LayoutInflater.from(this).inflate(
                inflateId,
                llShopItem,
                false
            )
            val textViewDescriptor: TextView = view.findViewById(R.id.textViewDescription)
            val textViewCount: TextView = view.findViewById(R.id.textViewCount)
            textViewDescriptor.text = shopItem.name
            textViewCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                viewModel.changeConditionShopItem(shopItem)
                true
            }
            llShopItem.addView(view)
        }
    }

}