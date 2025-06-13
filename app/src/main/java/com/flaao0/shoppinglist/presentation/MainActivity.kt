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
    private lateinit var shopItemAdapter: ShopItemAdapter

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
            shopItemAdapter.submitList(it)
        }

        val floatingActionButton = findViewById<FloatingActionButton>(
            R.id.floatingActionButton
        )

        floatingActionButton.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

    }


    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.recyclerViewShopItem)
        with(rvShopList) {
            shopItemAdapter = ShopItemAdapter()
            adapter = shopItemAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.IS_ENABLED,
                ShopItemAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.IS_DISABLED,
                ShopItemAdapter.MAX_POOL_SIZE
            )
        }
        setupClickListener()
        setupLongClickListener()


        val itemTouchHelper = setupSwipeListener()
        itemTouchHelper.attachToRecyclerView(rvShopList)

    }

    private fun setupSwipeListener(): ItemTouchHelper {
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
                val position = viewHolder.adapterPosition
                val shopItem = shopItemAdapter.currentList[position]
                viewModel.deleteShopItem(shopItem)
            }
        })
        return itemTouchHelper
    }

    private fun setupClickListener() {
        shopItemAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        shopItemAdapter.onShopItemLongClickListener = {
            viewModel.changeConditionShopItem(it)
        }
    }


}