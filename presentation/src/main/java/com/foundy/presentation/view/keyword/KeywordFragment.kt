package com.foundy.presentation.view.keyword

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.domain.model.Keyword
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentKeywordBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.google.android.material.snackbar.Snackbar

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
        binding.textInput.addTextChangedListener { text ->
            val keyword = text?.toString() ?: ""
            try {
                viewModel.checkValid(keyword)
                binding.textInputLayout.error = null
            } catch (e: Exception) {
                binding.textInputLayout.error = e.message ?: getString(R.string.invalid_keyword)
            }
        }
        binding.textInput.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    val keyword = (textView.text ?: "").toString()
                    try {
                        viewModel.checkValid(keyword)
                        addKeyword(keyword)
                        textView.text = ""
                    } catch (e: Exception) {
                        showSnackBar(e.message ?: getString(R.string.invalid_keyword))
                    }
                    true
                }
                else -> false
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
                progressBar.isVisible = false
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
            isEnabled = false
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}