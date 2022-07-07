package com.foundy.presentation.view.webview

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebSettingsCompat.FORCE_DARK_OFF
import androidx.webkit.WebSettingsCompat.FORCE_DARK_ON
import androidx.webkit.WebViewFeature
import com.foundy.domain.model.Notice
import com.foundy.presentation.R
import com.foundy.presentation.databinding.ActivityWebViewBinding
import com.google.gson.Gson


class WebViewActivity : AppCompatActivity() {

    private var _binding: ActivityWebViewBinding? = null
    private val binding: ActivityWebViewBinding get() = requireNotNull(_binding)

    companion object {
        /**
         * 웹 페이지의 이미지의 너비가 너무 커서 스크린 밖으로 벗어나지 않도록 하는 스크립트이다.
         *
         * 공식 페이지(?layout=unknown)의 경우 해당 문제가 없으나 이 앱에서 이용하는 url의 경우는 문제가 있다.
         */
        private const val IMAGE_WIDTH_SCRIPT = """
            var styles = `img{display: inline; height: auto; max-width: 100%;}`;
            var styleSheet = document.createElement("style");
            styleSheet.innerText = styles;
            document.head.appendChild(styleSheet);
        """

        /**
         * 페이지의 줌을 최대 3배까지 가능하게 하는 스크립트이다.
         *
         * 기존에는 1배까지로 줌을 지원하지 않는다. viewport에 접근하여 수정하고 있다.
         */
        private const val ZOOM_SETTING_SCRIPT = """
            var content = `initial-scale=1, minimum-scale=0.5, maximum-scale=3.0, user-scalable=yes`;
            document.getElementsByName('viewport')[0].setAttribute('content', content);
        """

        /**
         * 테이블의 너비를 고치는 스크립트이다.
         *
         * 한성대 공지 페이지에서 테이블의 너비가 고정되어 잘리는 문제를 해결한다. 모든 table과 td들의 너비를 무효화하고
         * `auto` 값을 가진 스타일 시트를 헤더에 추가한다.
         */
        private const val TABLE_WIDTH_SCRIPT = """
            const tables = document.getElementsByTagName("table");
            const tableDatas = document.getElementsByTagName("td");
            [...tables, ...tableDatas].forEach((element) => {
            element.style.width = null;
            });
            
            var styles = `td { width: auto;} table { width: 100%;}`;
            var styleSheet = document.createElement("style");
            styleSheet.innerText = styles;
            document.head.appendChild(styleSheet);
        """

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
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    try {
                        evaluateJavascript(
                            IMAGE_WIDTH_SCRIPT +
                                    ZOOM_SETTING_SCRIPT +
                                    TABLE_WIDTH_SCRIPT
                        ) {}
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            setDownloadListener { url, _, contentDisposition, mimetype, _ ->
                onDownload(url, contentDisposition, mimetype)
            }
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

    private fun onDownload(url: String, contentDisposition: String, mimeType: String) {
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                URLUtil.guessFileName(url, contentDisposition, mimeType)
            )
        }
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(
            applicationContext,
            getString(R.string.file_downloading),
            Toast.LENGTH_LONG
        ).show()
    }
}