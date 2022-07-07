package com.foundy.presentation.view.keyword

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.domain.exception.NoSearchResultException
import com.foundy.domain.model.Keyword
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentKeywordBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.foundy.presentation.extension.getProgressBarDrawable
import com.foundy.presentation.extension.setEndIconOnClickListenerWithDebounce
import com.foundy.presentation.extension.setOnEditorActionListenerWithDebounce
import com.foundy.presentation.utils.KeywordValidator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import retrofit2.HttpException

class KeywordFragment(
    @VisibleForTesting factory: (() -> ViewModelProvider.Factory)? = null
) : Fragment(R.layout.fragment_keyword) {

    private val viewModel: KeywordViewModel by activityViewModels(factory)

    companion object {

        private const val TAG = "KeywordFragment"

        const val MAX_KEYWORD_COUNT = 10
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentKeywordBinding.bind(view)

        initTextInput(binding)
        initRecyclerView(binding)
    }

    private fun initTextInput(binding: FragmentKeywordBinding) {
        binding.apply {
            textInput.addTextChangedListener { text ->
                onChangeText(text, textInputLayout)
            }
            textInput.setOnEditorActionListenerWithDebounce { actionId ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        onSubmitKeyword(binding)
                        true
                    }
                    else -> false
                }
            }
            textInputLayout.setEndIconOnClickListenerWithDebounce {
                onSubmitKeyword(binding)
            }
        }
    }

    private fun initRecyclerView(binding: FragmentKeywordBinding) {
        binding.apply {
            val adapter = KeywordAdapter(onClickDelete = ::removeKeyword, lifecycleScope)
            recyclerView.adapter = adapter
            recyclerView.addDividerDecoration(
                requireContext(),
                horizontalPaddingDimen = R.dimen.keyword_divider_horizontal_padding
            )
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            viewModel.keywordList.observe(viewLifecycleOwner) { result ->
                listProgressBar.isVisible = false
                if (result.isSuccess) {
                    val keywords = result.getOrNull()!!
                    adapter.submitList(keywords)
                    if (keywords.size >= MAX_KEYWORD_COUNT) {
                        disableTextInput(binding)
                    } else {
                        enableTextInput(binding)
                    }
                } else {
                    errorMsg.isVisible = true
                    textInputLayout.isVisible = false
                    keywordHelpText.isVisible = false
                    Log.e(TAG, "Failed to load keywords: ${result.exceptionOrNull()}")
                }
            }
        }
    }

    private fun onChangeText(editableText: Editable?, textInputLayout: TextInputLayout) {
        val keyword = editableText?.toString() ?: ""
        try {
            viewModel.checkValid(keyword)

            textInputLayout.error = null
        } catch (e: KeywordValidator.KeywordInvalidException) {
            textInputLayout.error = e.message ?: getString(R.string.invalid_keyword)
        }
    }

    private fun onSubmitKeyword(binding: FragmentKeywordBinding) {
        val keyword = binding.textInput.text?.toString() ?: ""
        changeEndIconToProgressBar(binding)

        viewModel.checkKeywordSubmit(
            keyword,
            onSuccess = {
                addKeyword(keyword)
                binding.textInput.setText("")
            },
            onFailure = { e ->
                when (e) {
                    is KeywordValidator.KeywordInvalidException -> {
                        showSnackBar(e.message ?: getString(R.string.invalid_keyword))
                    }
                    is NoSearchResultException -> {
                        showAlertDialog(e.message ?: getString(R.string.invalid_keyword)) {
                            addKeyword(keyword)
                            binding.textInput.setText("")
                        }
                    }
                    is HttpException -> {
                        showSnackBar(getString(R.string.check_internet_connection))
                    }
                    else -> {
                        showSnackBar(getString(R.string.cannot_create_keyword))
                    }
                }
            },
            onFinally = {
                changeEndIconToAddButton(binding)
            }
        )
    }

    private fun addKeyword(keyword: String) {
        viewModel.let {
            val keywordItem = Keyword(title = keyword)
            it.addKeywordItem(keywordItem)
            it.subscribeTo(keyword) { exception ->
                showSnackBar(getString(R.string.failed_to_subscribe_keyword))
                it.removeKeywordItem(keywordItem)
                Log.e(TAG, "Failed to Subscribe $keyword: ${exception.stackTrace}")
            }
        }
    }

    private fun removeKeyword(keyword: String) {
        viewModel.let {
            val keywordItem = Keyword(title = keyword)
            it.removeKeywordItem(keywordItem)
            it.unsubscribeFrom(keyword) { exception ->
                showSnackBar(getString(R.string.failed_to_unsubscribe_keyword))
                it.addKeywordItem(keywordItem)
                Log.e(TAG, "Failed to Unsubscribe $keyword: ${exception.stackTrace}")
            }
        }
    }

    private fun enableTextInput(binding: FragmentKeywordBinding) {
        binding.textInputLayout.apply {
            if (isEnabled) return@apply
            hint = getString(R.string.input_hint)
            isEnabled = true
        }
    }

    private fun disableTextInput(binding: FragmentKeywordBinding) {
        binding.textInputLayout.apply {
            if (!isEnabled) return@apply
            hint = getString(R.string.keyword_max_hint, MAX_KEYWORD_COUNT)
            error = null
            isEnabled = false
        }
    }

    private fun changeEndIconToProgressBar(binding: FragmentKeywordBinding) {
        val progressBar = requireContext().getProgressBarDrawable()
        binding.textInputLayout.endIconDrawable = progressBar
        (progressBar as? Animatable)?.start()
    }

    private fun changeEndIconToAddButton(binding: FragmentKeywordBinding) {
        binding.textInputLayout.endIconDrawable = requireContext().getDrawable(
            R.drawable.ic_baseline_add_box_24
        )
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(message: String, onSubmit: () -> Unit) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                setPositiveButton(R.string.submit) { _, _ -> onSubmit() }
                setNegativeButton(R.string.cancel) { _, _ -> }
                setTitle(message)
            }
            builder.create()
        }
        alertDialog?.show()
    }
}