package com.foundy.presentation.view.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.coroutinespermission.PermissionManager
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityMainBinding
import com.foundy.presentation.view.keyword.KeywordActivity
import com.foundy.presentation.view.search.SearchActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPostNotificationPermission()
        }

        val nhf = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = nhf.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.noticeFragment,
                R.id.favoriteFragment,
                R.id.cafeteriaFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        binding.apply {
            toolBar.setupWithNavController(navController, appBarConfiguration)
            toolBar.inflateMenu(R.menu.menu_main_option)
            toolBar.setOnMenuItemClickListener(::onMenuItemClick)
            bottomNav.setupWithNavController(navController)
        }

        viewModel.subscribeAllDbKeywords()
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_notification -> {
                startSearchActivity()
                true
            }
            R.id.notification_keyword -> {
                startKeywordActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSearchActivity() {
        val intent = SearchActivity.getIntent(this)
        startActivity(intent)
    }

    private fun startKeywordActivity() {
        val intent = KeywordActivity.getIntent(this)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPostNotificationPermission() {
        lifecycleScope.launch {
            val result = PermissionManager.requestPermissions(
                this@HomeActivity,
                1,
                Manifest.permission.POST_NOTIFICATIONS,
            )
            if (result !is PermissionResult.PermissionGranted) {
                showSnackBar(getString(R.string.please_grant_notification_permission))
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).apply {
            anchorView = binding.bottomNav
        }.show()
    }
}