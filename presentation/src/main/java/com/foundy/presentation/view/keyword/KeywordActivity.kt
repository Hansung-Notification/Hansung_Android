package com.foundy.presentation.view.keyword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foundy.domain.model.Keyword
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityKeywordBinding
import com.foundy.presentation.extension.addDividerDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordActivity : AppCompatActivity() {

    private var _binding: ActivityKeywordBinding? = null
    private val binding: ActivityKeywordBinding get() = requireNotNull(_binding)

    private val viewModel: KeywordViewModel by viewModels()

    companion object {

        private const val TAG = "KeywordActivity"

        const val MAX_KEYWORD_COUNT = 10

        fun getIntent(context: Context): Intent {
            return Intent(context, KeywordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityKeywordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolBar()
        initTextInput()
        initRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initToolBar() {
        binding.keywordToolBar.apply {
            title = resources.getString(R.string.notification_keyword)
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTextInput() {
        binding.textInput.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    // TODO: 공백 입력은 제외하도록 예외처리
                    val text = (textView.text ?: "").toString()
                    if (text.length < 2) {
                        showSnackBar(getString(R.string.keyword_min_length_warning))
                    } else if (viewModel.hasKeyword(text)) {
                        showSnackBar(getString(R.string.already_exists_keyword))
                    } else {
                        addKeyword(text)
                        textView.text = ""
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            val adapter = KeywordAdapter(onClickDelete = ::removeKeyword, lifecycleScope)
            recyclerView.adapter = adapter
            recyclerView.addDividerDecoration(
                this@KeywordActivity,
                horizontalPaddingDimen = R.dimen.keyword_divider_horizontal_padding
            )
            recyclerView.layoutManager = LinearLayoutManager(this@KeywordActivity)

            viewModel.keywordList.observe(this@KeywordActivity) { keywords ->
                // TODO: 공백 입력은 제외하도록 예외처리
                adapter.submitList(keywords)
                if (keywords.size >= MAX_KEYWORD_COUNT) {
                    disableTextInput()
                } else {
                    enableTextInput()
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

    private fun enableTextInput() {
        binding.textInputLayout.apply {
            if (isEnabled) return@apply
            hint = getString(R.string.input_hint)
            isEnabled = true
        }
    }

    private fun disableTextInput() {
        binding.textInputLayout.apply {
            if (!isEnabled) return@apply
            hint = getString(R.string.keyword_max_hint, MAX_KEYWORD_COUNT)
            isEnabled = false
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}