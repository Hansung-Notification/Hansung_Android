package com.foundy.presentation.view.keyword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityKeywordBinding
import com.foundy.presentation.view.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * 사용자가 로그인 했으면 [KeywordFragment]를 보이고, 로그인 되어있지 않다면 [LoginFragment]를 보인다.
 */
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
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isSignedIn()) {
            navigateToKeywordFragment()
        }
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

    private fun navigateToKeywordFragment() {
        findNavController(R.id.keyword_nav_host_fragment).navigate(R.id.action_loginFragment_to_keywordFragment)
    }
}