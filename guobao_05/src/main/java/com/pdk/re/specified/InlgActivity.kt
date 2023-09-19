package com.pdk.re.specified

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.common.util.Base64Utils
import com.just.agentweb.AgentWeb
import com.pdk.re.specified.utils.Base64Util
import com.pdk.re.specified.utils.RSAUtil
import org.json.JSONObject
import java.nio.charset.Charset


class InlgActivity : AppCompatActivity() {

    private lateinit var agentWeb: AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppsFlyerLib.getInstance().start(this, "aXFxnSE6DkWerjca55cJeP")
        window.statusBarColor = Color.parseColor("#194C38")
        setContentView(R.layout.activity_inlg)
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(findViewById(R.id.app_container), ViewGroup.LayoutParams(-1, -1))
            .closeIndicator()
            .setMainFrameErrorView(TextView(this))
            .addJavascriptInterface("jsBridge", this)
            .createAgentWeb()
            .go(getUrl())
            .apply {
                this.webCreator.webView.setBackgroundColor(Color.parseColor("#194C38"))
                webCreator.webView.settings.useWideViewPort = true
                webCreator.webView.settings.loadWithOverviewMode = true
                webCreator.webView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.91 Mobile Safari/537.36"
            }
        WebView.setWebContentsDebuggingEnabled(true)
//        val appSecret =
    }

    private fun getAppSecret() : String {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        return Base64.decode(getString(R.string.app_secret), Base64.DEFAULT).toString(Charset.defaultCharset())
    }

    private fun getUrl() = this.intent.getStringExtra("url")?: "https://166bet.com/?id=25124822"

    @JavascriptInterface
    fun postMessage(name: String, data: String) {
        if (name == "openWindow") {
        } else {
            AppsFlyerLib.getInstance().logEvent(this, name, hashMapOf<String, Any?>().apply {
                try {
                    val jsonData = JSONObject(data)
                    jsonData.keys().forEach {
                        val key = when (it) {
                            "currency"  -> AFInAppEventParameterName.CURRENCY
                            "amount" -> AFInAppEventParameterName.REVENUE
                            else -> it
                        }
                        this[key] = jsonData.opt(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        agentWeb.webCreator.webView.evaluateJavascript("javascript:window.closeGame()") {}
    }

    override fun onBackPressed() {
        if (!agentWeb.back()) {
            super.onBackPressed()
        }
    }
}