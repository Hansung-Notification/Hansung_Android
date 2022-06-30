package com.foundy.presentation.view.keyword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
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
        fun getIntent(context: Context): Intent {
            return Intent(context, KeywordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityKeywordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolBar()
        initTextInputEvent()
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

    private fun initTextInputEvent() {
        binding.textInput.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    val text = textView.text ?: ""
                    // TODO: 이미 존재하는 단어는 예외처리 해야함
                    if (text.length < 2) {
                        showSnackBar(getString(R.string.keyword_min_length_warning))
                    } else {
                        addKeyword(text.toString())
                        textView.text = ""
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecyclerView() {
        val adapter = KeywordAdapter()
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.addDividerDecoration(this@KeywordActivity)
            recyclerView.layoutManager = LinearLayoutManager(this@KeywordActivity)

            viewModel.keywordList.observe(this@KeywordActivity) { keywords ->
                adapter.submitList(keywords)
            }
        }
    }

    private fun addKeyword(keyword: String) {
        viewModel.addKeywordItem(Keyword(title = keyword))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}