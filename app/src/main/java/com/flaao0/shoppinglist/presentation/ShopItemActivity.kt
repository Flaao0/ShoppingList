package com.flaao0.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.flaao0.shoppinglist.R
import com.flaao0.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

//    private lateinit var viewModel: ShopItemViewModel
//    private lateinit var textInputLayoutName: TextInputLayout
//    private lateinit var textInputLayoutCount: TextInputLayout
//    private lateinit var textInputEditTextName: TextInputEditText
//    private lateinit var textInputEditTextCount: TextInputEditText
//    private lateinit var buttonSave: Button
//
//    private var screenMode = MODE_UNKNOWN
//    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        parseIntent()
//        initViews()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        when (screenMode) {
//            MODE_ADD -> addShopItem()
//            MODE_EDIT -> editShopItem()
//        }

    }

//    private fun addShopItem() {
//        buttonSave.setOnClickListener {
//            val name = textInputEditTextName.text.toString()
//            val count = textInputEditTextCount.text.toString()
//            errorInputName()
//            errorInputCount()
//            viewModel.addShopItem(name, count)
//            viewModel.isClosing.observe(this) {
//                finish()
//            }
//        }
//
//    }
//
//    private fun editShopItem() {
//        viewModel.getShopItemById(shopItemId)
//        viewModel.shopItemLD.observe(this) {
//            Log.d("ShopItemActivity1", it.toString())
//            val name = it.name
//            val count = it.count
//            textInputEditTextName.setText(name)
//            textInputEditTextCount.setText(count.toString())
//
//            buttonSave.setOnClickListener {
//                val inputName = textInputEditTextName.text.toString()
//                val inputCount = textInputEditTextCount.text.toString()
//                errorInputName()
//                errorInputCount()
//                viewModel.editShopItem(inputName, inputCount)
//                viewModel.isClosing.observe(this) {
//                    finish()
//                }
//            }
//        }
//    }
//
//    private fun errorInputName() {
//        viewModel.errorInputName.observe(this) {
//            if (it) {
//                textInputLayoutName.error = "error name"
//                textInputEditTextName.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        count: Int,
//                        after: Int
//                    ) {
//
//                    }
//
//                    override fun onTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        before: Int,
//                        count: Int
//                    ) {
//                        textInputLayoutName.error = null
//                        viewModel.resetErrorInputName()
//                    }
//
//                    override fun afterTextChanged(s: Editable?) {
//
//                    }
//                })
//            }
//        }
//    }
//
//    private fun errorInputCount() {
//        viewModel.errorInputCount.observe(this) {
//            if (it) {
//                textInputLayoutCount.error = "error count"
//
//                textInputEditTextCount.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        count: Int,
//                        after: Int
//                    ) {
//
//                    }
//
//                    override fun onTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        before: Int,
//                        count: Int
//                    ) {
//                        textInputLayoutCount.error = null
//                        viewModel.resetErrorInputCount()
//                    }
//
//                    override fun afterTextChanged(s: Editable?) {
//                    }
//                })
//            }
//        }
//    }
//
//    private fun initViews() {
//        textInputLayoutName = findViewById(R.id.til_name)
//        textInputEditTextName = findViewById(R.id.et_name)
//        textInputLayoutCount = findViewById(R.id.til_count)
//        textInputEditTextCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.btn_save)
//    }
//
//    private fun parseIntent() {
//        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
//            throw RuntimeException("Param screen mode is absent")
//        }
//        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        if (mode != MODE_ADD && mode != MODE_EDIT) {
//            throw IllegalArgumentException("unknown mode: ${mode.toString()}")
//        }
//        screenMode = mode
//        if (screenMode == MODE_EDIT) {
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
//                throw RuntimeException("Param shop item id is absent")
//            }
//            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
//        }
//    }

    companion object {

        const val EXTRA_SCREEN_MODE = "extra_mode"
        const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""


        fun newAddIntent(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newEditIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}