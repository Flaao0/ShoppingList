package com.flaao0.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.flaao0.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopItemAdapter
    private lateinit var button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        button = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        button.setOnClickListener {
            val intent = ShopItemActivity.newAddIntent(this)
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.recyclerViewShopItem)
        adapter = ShopItemAdapter()
        rvShopList.adapter = adapter

        adapter.onShopItemLongClickListener = {
            viewModel.changeConditionShopItem(it)
        }

        adapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newEditIntent(this, it.id)
            startActivity(intent)
        }



        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val shopItemPosition = viewHolder.adapterPosition
                val shopItem = adapter.currentList[shopItemPosition]
                viewModel.deleteShopItem(shopItem)
            }
        })

        itemTouchHelper.attachToRecyclerView(rvShopList)

    }



}







