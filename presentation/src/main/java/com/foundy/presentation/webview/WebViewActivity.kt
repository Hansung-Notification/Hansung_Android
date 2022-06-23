package com.foundy.presentation.webview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
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
        binding.webView.apply {
            webViewClient = WebViewClient()
        }.loadUrl(notice.url)
    }
}