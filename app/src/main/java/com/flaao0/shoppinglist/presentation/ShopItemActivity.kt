package com.flaao0.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.flaao0.shoppinglist.R
import com.flaao0.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    private var screenMode = UNDEFINED_STRING
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        parseIntent()
        initViews()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }


    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            val inputName = tilName.editText?.text.toString().trim()
            val inputCount = tilCount.editText?.text.toString().trim()
            viewModel.addShopItem(inputName, inputCount)
        }
        tilErrors()
        addNameTextListener()
        addCountTextListener()
        closeScreen()
    }


    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItemLD.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        btnSave.setOnClickListener {
            viewModel.editShopItem(etName.text.toString(), etCount.text.toString())
        }
        tilErrors()
        addNameTextListener()
        addCountTextListener()
        closeScreen()
    }

    private fun tilErrors() {
        viewModel.errorInputName.observe(this) { isError ->
            if (isError == true) tilName.error = "Error"
        }
        viewModel.errorInputCount.observe(this) { isError ->
            if (isError == true) tilCount.error = "Error"
        }
    }

    private fun closeScreen() {
        viewModel.isClosing.observe(this) {
            finish()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }


    }

    private fun addCountTextListener() {
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.resetErrorInputCount()
                tilCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun addNameTextListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.resetErrorInputName()
                tilName.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        btnSave = findViewById(R.id.btn_save)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        private const val UNDEFINED_STRING = ""


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, itemId)
            return intent
        }


    }


}