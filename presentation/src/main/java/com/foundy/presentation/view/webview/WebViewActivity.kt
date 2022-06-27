package com.foundy.presentation.view.webview

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import androidx.webkit.WebViewFeature
import com.foundy.domain.model.Notice
import com.foundy.presentation.databinding.ActivityWebViewBinding
import com.google.gson.Gson


class WebViewActivity : AppCompatActivity() {

    private var _binding: ActivityWebViewBinding? = null
    private val binding: ActivityWebViewBinding get() = requireNotNull(_binding)

    companion object {
        fun getIntent(context: Context, notice: Notice): Intent {
            return Intent(context, WebViewActivity::class.java)
                .putExtra("notice", Gson().toJson(notice))
        }
    }

    private fun getNoticeFromIntent(): Notice {
        val json = intent.getStringExtra("notice")
        return Gson().fromJson(json, Notice::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notice = getNoticeFromIntent()
        initToolBar(notice)
        initWebView(notice)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initToolBar(notice: Notice) {
        binding.webViewToolBar.apply {
            title = notice.title
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initWebView(notice: Notice) {
        binding.webView.apply {
            webViewClient = WebViewClient()
        }.loadUrl(notice.url)
        setWebViewThemeMode()
    }

    private fun setWebViewThemeMode() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    WebSettingsCompat.setForceDark(binding.webView.settings, FORCE_DARK_ON)
                }
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    WebSettingsCompat.setForceDark(binding.webView.settings, FORCE_DARK_OFF)
                }
            }
        }
    }
}