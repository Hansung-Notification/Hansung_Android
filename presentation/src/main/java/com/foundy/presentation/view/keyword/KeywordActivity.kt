package com.foundy.presentation.view.keyword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityKeywordBinding

class KeywordActivity : AppCompatActivity() {

    private var _binding: ActivityKeywordBinding? = null
    private val binding: ActivityKeywordBinding get() = requireNotNull(_binding)

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
}