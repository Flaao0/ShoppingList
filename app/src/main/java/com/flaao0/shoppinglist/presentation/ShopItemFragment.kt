package com.flaao0.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flaao0.shoppinglist.R
import com.flaao0.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private var screenMode: String = MODE_UNKNOWN,
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var buttonSave: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_shop_item,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        initViews(view)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        launchRightMode()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> addShopItem()
            MODE_EDIT -> editShopItem()
        }
    }

    private fun observeViewModel() {
        viewModel.isClosing.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun addShopItem() {
        buttonSave.setOnClickListener {
            val name = textInputEditTextName.text.toString()
            val count = textInputEditTextCount.text.toString()
            errorInputName()
            errorInputCount()
            viewModel.addShopItem(name, count)
        }

    }

    private fun editShopItem() {
        viewModel.getShopItemById(shopItemId)
        viewModel.shopItemLD.observe(viewLifecycleOwner) {
            val name = it.name
            val count = it.count
            textInputEditTextName.setText(name)
            textInputEditTextCount.setText(count.toString())

            buttonSave.setOnClickListener {
                val inputName = textInputEditTextName.text.toString()
                val inputCount = textInputEditTextCount.text.toString()
                errorInputName()
                errorInputCount()
                viewModel.editShopItem(inputName, inputCount)
            }
        }
    }

    private fun errorInputName() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                textInputLayoutName.error = "error name"
                textInputEditTextName.addTextChangedListener(object : TextWatcher {
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
                        textInputLayoutName.error = null
                        viewModel.resetErrorInputName()
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })
            }
        }
    }

    private fun errorInputCount() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                textInputLayoutCount.error = "error count"

                textInputEditTextCount.addTextChangedListener(object : TextWatcher {
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
                        textInputLayoutCount.error = null
                        viewModel.resetErrorInputCount()
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            }
        }
    }

    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.til_name)
        textInputEditTextName = view.findViewById(R.id.et_name)
        textInputLayoutCount = view.findViewById(R.id.til_count)
        textInputEditTextCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.btn_save)
    }

    private fun parseParams() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw IllegalArgumentException("unknown mode: $screenMode")
        }

        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Param shop item id is absent")
        }
    }

    companion object {

        const val EXTRA_SCREEN_MODE = "extra_mode"
        const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }


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
