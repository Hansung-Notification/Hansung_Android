package com.foundy.presentation.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityMainBinding
import com.foundy.presentation.view.keyword.KeywordActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nhf = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = nhf.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.noticeFragment, R.id.favoriteFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        binding.apply {
            toolBar.setupWithNavController(navController, appBarConfiguration)
            toolBar.inflateMenu(R.menu.menu_main_option)
            toolBar.setOnMenuItemClickListener(::onMenuItemClick)
            bottomNav.setupWithNavController(navController)
        }
    }

    override fun onStart() {
        super.onStart()
        checkUserAuth()
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notification_keyword -> {
                startKeywordActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkUserAuth() {
        val auth = Firebase.auth
        if (auth.currentUser == null) {
            auth.signInAnonymously().addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Snackbar.make(
                        binding.root,
                        getString(R.string.authentication_failed),
                        Snackbar.LENGTH_LONG
                    ).apply {
                        anchorView = binding.bottomNav
                    }.show()
                }
            }
        }
    }

    private fun startKeywordActivity() {
        val intent = KeywordActivity.getIntent(this)
        startActivity(intent)
    }
}