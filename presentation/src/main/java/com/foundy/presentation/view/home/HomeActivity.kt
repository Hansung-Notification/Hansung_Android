package com.foundy.presentation.view.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityMainBinding
import com.foundy.presentation.view.keyword.KeywordActivity
import com.foundy.presentation.view.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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
}