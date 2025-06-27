package com.flaao0.shoppinglist.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TEST12", "onAttach")
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity don't implement interface")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TEST12", "onCreate")
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TEST12", "onCreateView")
        return inflater.inflate(
            R.layout.fragment_shop_item,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST12", "onViewCreated")
        initViews(view)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        launchRightMode()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST12", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST12", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST12", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST12", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TEST12", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST12", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TEST12", "onDetach")
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> addShopItem()
            MODE_EDIT -> editShopItem()
        }
    }

    private fun observeViewModel() {
        viewModel.isClosing.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
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
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw IllegalArgumentException("unknown mode: $screenMode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }


    companion object {

        const val SCREEN_MODE = "extra_mode"
        const val SHOP_ITEM_ID = "extra_shop_item_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}
