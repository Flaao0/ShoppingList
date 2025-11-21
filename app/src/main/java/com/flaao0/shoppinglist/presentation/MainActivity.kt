package com.flaao0.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.flaao0.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject
import kotlin.io.path.Path

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopItemAdapter
    private lateinit var button: FloatingActionButton
    private var fragmentContainer: FragmentContainerView? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val component by lazy {
        (application as ShopApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        fragmentContainer = findViewById(R.id.fragment_container_view)


        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        button = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        button.setOnClickListener {
            if (fragmentContainer != null) {
                val fragment = ShopItemFragment.newInstanceAddItem()
                launchFragment(fragment)
            } else {
                val intent = ShopItemActivity.newAddIntent(this)
                startActivity(intent)
            }

        }

    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }

    private fun launchFragment(shopItemFragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, shopItemFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.recyclerViewShopItem)
        adapter = ShopItemAdapter()
        rvShopList.adapter = adapter

        adapter.onShopItemLongClickListener = {
            viewModel.changeConditionShopItem(it)
        }

        adapter.onShopItemClickListener = {
            if (fragmentContainer != null) {
                val fragmentEdit = ShopItemFragment.newInstanceEditItem(it.id)
                launchFragment(fragmentEdit)
            } else {
                val intent = ShopItemActivity.newEditIntent(this, it.id)
                startActivity(intent)
            }

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







